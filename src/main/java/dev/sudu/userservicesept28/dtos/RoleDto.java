package dev.sudu.userservicesept28.dtos;

import dev.sudu.userservicesept28.models.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {
    private Long id;
    private String name;

    public static RoleDto from(Role role) {
        if(role == null) return null;

        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }
}
