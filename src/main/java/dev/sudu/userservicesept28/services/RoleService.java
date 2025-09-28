package dev.sudu.userservicesept28.services;

import dev.sudu.userservicesept28.models.Role;

import java.util.List;

public interface RoleService {
    Role addRole(String name);

    Role updateRole(Long role_id, String name);

    Role deleteRole(Long role_id);

    List<Role> getAllRoles(Integer pageNumber, Integer pageSize);

    Role getRoleById(Long role_id);
}
