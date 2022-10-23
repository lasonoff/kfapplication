package ru.yauroff.kfapplication.kfapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yauroff.kfapplication.kfapp.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getByName(String name);
}
