package dev.sudu.userservicesept28.models;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@Entity
public class Role extends BaseModel {
    private String name;

    public Role() {}

    public Role(RoleBuilder roleBuilder) {
        Assert.hasText(roleBuilder.getRoleName(), "Role name cannot be empty");
        this.name = roleBuilder.getRoleName();
    }

    public static RoleBuilder builder() {
        return new RoleBuilder();
    }

    public static class RoleBuilder {
        private String roleName;

        public String getRoleName() {
            return roleName;
        }

        public RoleBuilder setRoleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public Role build() {
            return new Role(this);
        }
    }
}
