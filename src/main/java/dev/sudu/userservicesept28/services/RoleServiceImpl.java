package dev.sudu.userservicesept28.services;

import dev.sudu.userservicesept28.models.Role;
import dev.sudu.userservicesept28.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role addRole(String name) {
        Assert.hasText(name, "Name must not be empty");

        Optional<Role> fetchedRole = roleRepository.getByName(name);

        if(fetchedRole.isPresent()) {
            throw new RuntimeException("Role with name " + name + " already exists");
        }

        return roleRepository.save(Role.builder().setRoleName(name).build());
    }

    @Override
    public Role updateRole(Long role_id, String name) {
        Assert.hasText(name, "Name must not be empty");
        if(role_id == null) {
            throw new RuntimeException("Role id must not be null");
        }

        Optional<Role> fetchedRole = roleRepository.findById(role_id);
        if(fetchedRole.isEmpty()) {
            throw new RuntimeException("Role with id " + role_id + " does not exist");
        }

        Role roleToSave = fetchedRole.get();
        roleToSave.setName(name);
        return roleRepository.save(roleToSave);
    }

    @Override
    public Role deleteRole(Long role_id) {
        if(role_id == null) {
            throw new RuntimeException("Role id must not be null");
        }

        Optional<Role> fetchedRole = roleRepository.findById(role_id);

        if(fetchedRole.isEmpty()) {
            throw new RuntimeException("Role with id " + role_id + " does not exist");
        }

        roleRepository.delete(fetchedRole.get());
        return fetchedRole.get();
    }

    public List<Role> getAllRoles(Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        return roleRepository.findAll(pageRequest).getContent();
    }

    @Override
    public Role getRoleById(Long role_id) {
        if(role_id == null) {
            throw new RuntimeException("Role id must not be null");
        }

        Optional<Role> fetchedRole = roleRepository.findById(role_id);
        return fetchedRole.get();
    }
}
