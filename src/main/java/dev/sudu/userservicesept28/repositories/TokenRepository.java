package dev.sudu.userservicesept28.repositories;

import dev.sudu.userservicesept28.models.Token;
import dev.sudu.userservicesept28.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByTokenAndExpiryAtGreaterThan(String token, Long expiryAt);

    Optional<Token> findByToken(String token);

    List<Token> findAllByUserAndIsActive(User user, boolean isActive);
}
