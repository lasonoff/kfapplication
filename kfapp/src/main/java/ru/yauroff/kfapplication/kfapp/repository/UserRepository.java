package ru.yauroff.kfapplication.kfapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yauroff.kfapplication.kfapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByLogin(String login);

    User getByEmail(String email);
}
