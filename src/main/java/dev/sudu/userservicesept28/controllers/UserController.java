package dev.sudu.userservicesept28.controllers;

import dev.sudu.userservicesept28.dtos.LoginRequestDto;
import dev.sudu.userservicesept28.dtos.LoginResponseDto;
import dev.sudu.userservicesept28.dtos.SignUpRequestDto;
import dev.sudu.userservicesept28.dtos.SignUpResponseDto;
import dev.sudu.userservicesept28.models.Token;
import dev.sudu.userservicesept28.models.User;
import dev.sudu.userservicesept28.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.signup(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword());
        return SignUpResponseDto.from(user);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        Token token = userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        return LoginResponseDto.from(token);
    }

    @PatchMapping("/validate/{tokenValue}")
    public LoginResponseDto validate(@PathVariable("tokenValue") String tokenValue) {
        Token token = userService.validate(tokenValue);
        return LoginResponseDto.from(token);
    }

    @GetMapping("/logout/{tokenValue}")
    public LoginResponseDto logout(@PathVariable("tokenValue") String tokenValue) {
        Token token = userService.logout(tokenValue);
        return LoginResponseDto.from(token);
    }
}
