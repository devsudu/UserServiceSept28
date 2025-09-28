package dev.sudu.userservicesept28.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String token;
    private Long expiryAt;
    private Boolean isActive;
    @ManyToOne
    private User user;

    // Token User  ==> M:1
    //    1    1
    //    M    1

    public Token() {}

    public Token(TokenBuilder tokenBuilder){
        Assert.hasText(tokenBuilder.token, "token generation failed");
        Assert.notNull(tokenBuilder.user, "user generation failed");
//        Assert.isNull(tokenBuilder.expiryAt, "expiryAt cannot be null");

        this.token = tokenBuilder.token;
        this.expiryAt = tokenBuilder.expiryAt;
        this.isActive = tokenBuilder.isActive;
        this.user = tokenBuilder.user;
    }

    public static TokenBuilder builder(){
        return new TokenBuilder();
    }

    public static class TokenBuilder {
        private String token;
        private Long expiryAt;
        private Boolean isActive;
        private User user;

        public String getToken() {
            return token;
        }

        public TokenBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public Long getExpiryAt() {
            return expiryAt;
        }

        public TokenBuilder setExpiryAt(Long expiryAt) {
            this.expiryAt = expiryAt;
            return this;
        }

        public Boolean getActive() {
            return isActive;
        }

        public TokenBuilder setActive(Boolean active) {
            isActive = active;
            return this;
        }

        public User getUser() {
            return user;
        }

        public TokenBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public Token build(){
            return new Token(this);
        }
    }
}
