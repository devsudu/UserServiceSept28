package dev.sudu.userservicesept28.services;

import dev.sudu.userservicesept28.dtos.LoginResponseDto;
import dev.sudu.userservicesept28.dtos.SignUpResponseDto;
import dev.sudu.userservicesept28.models.Token;
import dev.sudu.userservicesept28.models.User;

public interface UserService {
    User signup(String name, String email, String password);

    Token login(String email, String password);

    Token validate(String token);

    Token logout(String token);

}
