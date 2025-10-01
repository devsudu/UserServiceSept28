package dev.sudu.userservicesept28.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sudu.userservicesept28.dtos.SendEmailDto;
import dev.sudu.userservicesept28.models.Token;
import dev.sudu.userservicesept28.models.User;
import dev.sudu.userservicesept28.repositories.TokenRepository;
import dev.sudu.userservicesept28.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.kafka.core.KafkaTemplate;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SecretKey secretKey;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public User signup(String name, String email, String password) {
        Assert.hasText(name, "Name cannot be empty");
        Assert.hasText(email, "Email cannot be empty");
        Assert.hasText(password, "Password cannot be empty");

        Optional<User> fetchedUser = userRepository.findByEmail(email);
        if(fetchedUser.isPresent()) {
            throw new RuntimeException("User with email " + email + " already exists");
        }

        User savedUser = userRepository.save(User.builder().setName(name).setEmail(email).setPassword(bCryptPasswordEncoder.encode(password)).build());

        // Push a message to kafka to send a welcome email.

        SendEmailDto emailDto = new SendEmailDto();
        emailDto.setEmail(savedUser.getEmail());
        emailDto.setSubject("Welcome " + savedUser.getName());
        emailDto.setBody("Welcome to our platform");

        try {
            kafkaTemplate.send("sendWelcomeEmail", objectMapper.writeValueAsString(emailDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return savedUser;
    }

    @Override
    public Token login(String email, String password) {
        Assert.hasText(email, "Email cannot be empty");
        Assert.hasText(password, "Password cannot be empty");

        Optional<User> fetchedUser = userRepository.findByEmail(email);

        if(fetchedUser.isEmpty()) {
            throw new RuntimeException("User with email " + email + " does not exist, pls signup");
        }

        if(!bCryptPasswordEncoder.matches(password, fetchedUser.get().getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        List<Token> tokenList = tokenRepository.findAllByUserAndIsActive(fetchedUser.get(), true);
        if(!tokenList.isEmpty()){
            tokenList.stream().iterator().forEachRemaining(token -> token.setIsActive(false));
            tokenRepository.saveAll(tokenList);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", fetchedUser.get().getEmail());
        claims.put("name", fetchedUser.get().getName());
//        claims.put("role", fetchedUser.get().getRoles());
        claims.put("iss", "sudu.com");
        Long expirtyAt = System.currentTimeMillis() + (60 * 60 * 1000);
        claims.put("exp", expirtyAt);

        String token = Jwts.builder().claims(claims).signWith(secretKey).compact();

        Token tokenToSave = Token.builder().setToken(token).setUser(fetchedUser.get()).setActive(true).setExpiryAt(expirtyAt).build();
        return tokenRepository.save(tokenToSave);
    }

    @Override
    public Token validate(String token) {
        Assert.hasText(token, "Token cannot be empty");

        JwtParser jwtParser = Jwts.parser().verifyWith(secretKey).build();
        Claims claims = jwtParser.parseSignedClaims(token).getPayload();

        Long expiryAt = (Long) claims.get("exp");

        if(expiryAt < System.currentTimeMillis()) {
            throw new RuntimeException("Expired token");
        }

        Optional<Token> fetchedToken = tokenRepository.findByTokenAndExpiryAtGreaterThan(token, System.currentTimeMillis());

        if(fetchedToken.isEmpty()){
            throw new RuntimeException("Token not found");
        }

        if(!fetchedToken.get().getIsActive()){
            throw new RuntimeException("User logged out");
        }

        return fetchedToken.get();
    }

    @Override
    public Token logout(String token) {
        Optional<Token> fetchedToken = tokenRepository.findByToken(token);

        if(fetchedToken.isEmpty()){
            throw new RuntimeException("Invalid token");
        }

        fetchedToken.get().setIsActive(false);
        return tokenRepository.save(fetchedToken.get());
    }
}
