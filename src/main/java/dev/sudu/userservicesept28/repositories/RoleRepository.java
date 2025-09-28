package dev.sudu.userservicesept28.repositories;

import dev.sudu.userservicesept28.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getByName(String name);

    @Override
    Role save(Role role);
}
