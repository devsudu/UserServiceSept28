package dev.sudu.userservicesept28.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;
    private String email;
    private String password;
    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Role> roles;

    // User Role ==> M:M
    //   1    M
    //   M    1

    public User() {}

    public User(UserBuilder userBuilder) {
        Assert.hasText(userBuilder.getName(), "Name is required");
        Assert.hasText(userBuilder.getEmail(), "Email is required");
        Assert.hasText(userBuilder.getPassword(), "Password is required");
//        Assert.notEmpty(userBuilder.getRoles(), "Roles are required");

        this.name = userBuilder.getName();
        this.email = userBuilder.getEmail();
        this.password = userBuilder.getPassword();
        List<Role> roles = new ArrayList<>();
        roles.add(Role.builder().setRoleName("user").build());
        this.roles = roles;
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String name;
        private String email;
        private String password;
        private List<Role> roles;

        public String getName() {
            return name;
        }

        public UserBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public String getEmail() {
            return email;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getPassword() {
            return password;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public List<Role> getRoles() {
            return roles;
        }

        public UserBuilder setRoles(List<Role> roles) {
            this.roles = roles;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
