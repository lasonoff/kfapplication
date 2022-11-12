package ru.yauroff.kfapplication.kfapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfapp.service.exceptions.UserRegistrationException;

@Service
public interface UserService extends UserDetailsService {
    User findByLogin(String login);

    User findByEmail(String email);

    User registerNewUser(User user) throws UserRegistrationException;
}
