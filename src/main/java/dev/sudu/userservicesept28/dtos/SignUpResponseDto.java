package dev.sudu.userservicesept28.dtos;

import dev.sudu.userservicesept28.models.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
public class SignUpResponseDto {
    private String name;
    private String email;
    private Boolean isLoggedIn;

    public static SignUpResponseDto from(User user) {
        if(user == null) return null;

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();
        signUpResponseDto.setName(user.getName());
        signUpResponseDto.setEmail(user.getEmail());
        signUpResponseDto.setIsLoggedIn(false);
        return signUpResponseDto;
    }
}
