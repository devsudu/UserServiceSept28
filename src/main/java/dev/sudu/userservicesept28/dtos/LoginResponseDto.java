package dev.sudu.userservicesept28.dtos;

import dev.sudu.userservicesept28.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String token;
    private String email;
    private Long expiryAt;
    private Boolean isLoggedIn;

    public static LoginResponseDto from(Token token) {
        if(token == null) return null;

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token.getToken());
        loginResponseDto.setEmail(token.getUser().getEmail());
        loginResponseDto.setExpiryAt(token.getExpiryAt());
        loginResponseDto.setIsLoggedIn(token.getIsActive());
        return loginResponseDto;
    }
}
