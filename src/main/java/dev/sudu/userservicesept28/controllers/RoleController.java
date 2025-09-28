package dev.sudu.userservicesept28.controllers;

import dev.sudu.userservicesept28.dtos.RoleDto;
import dev.sudu.userservicesept28.models.Role;
import dev.sudu.userservicesept28.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping
    public RoleDto addRole(@RequestBody RoleDto roleDto) {
        return RoleDto.from(roleService.addRole(roleDto.getName()));
    }

    @PatchMapping
    public RoleDto updateRole(@RequestBody RoleDto roleDto) {
        return RoleDto.from(roleService.updateRole(roleDto.getId(), roleDto.getName()));
    }

    @DeleteMapping("/{role_id}")
    public RoleDto deleteRole(@PathVariable("role_id") Long roleId) {
        return RoleDto.from(roleService.deleteRole(roleId));
    }

    @GetMapping("/{role_id}")
    public RoleDto getRoleById(@PathVariable("role_id") Long roleId) {
        return RoleDto.from(roleService.getRoleById(roleId));
    }

    @GetMapping("/{pageNumber}/{pageSize}")
    public List<Role> getAllRoles(@PathVariable("pageNumber") Integer pageNumber, @PathVariable("pageSize") Integer pageSize) {
        return roleService.getAllRoles(pageNumber, pageSize);
    }
}
