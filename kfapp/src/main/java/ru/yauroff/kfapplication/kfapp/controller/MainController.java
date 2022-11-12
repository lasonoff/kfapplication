package ru.yauroff.kfapplication.kfapp.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yauroff.kfapplication.kfapp.dto.RegistrationUserDto;
import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfapp.service.UserService;
import ru.yauroff.kfapplication.kfapp.service.exceptions.UserRegistrationException;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homePage(Authentication authentication, ModelMap model) {
        String login = authentication == null ? null : authentication.getName();
        if (login != null) {
            User user = userService.findByLogin(login);
            model.put("username", user.getLastname());
        }
        model.put("authenticated", authentication != null);
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestParam Map<String, String> registrationParams, ModelMap model) {
        RegistrationUserDto registrationUser = RegistrationUserDto.fromParamsMap(registrationParams);
        if (registrationUser == null || registrationUser.getLogin() == null || registrationUser.getPassword() == null ||
                registrationUser.getEmail() == null) {
            model.put("errorText", "Incorrect user information!");
            return "registration";
        }
        User user = userService.findByLogin(registrationUser.getLogin());
        if (user != null) {
            model.put("errorText", "User with login '" + registrationUser.getLogin() + "' already exists!");
            return "registration";
        }
        user = userService.findByEmail(registrationUser.getEmail());
        if (user != null) {
            model.put("errorText", "User with email '" + registrationUser.getEmail() + "' already exists!");
            return "registration";
        }
        User userForRegistration = registrationUser.toUser();
        userForRegistration.setPassword(passwordEncoder.encode(userForRegistration.getPassword()));
        try {
            userService.registerNewUser(userForRegistration);
        } catch (UserRegistrationException e) {
            model.put("errorText", e.getMessage());
            return "registration";
        }
        model.put("user", userForRegistration);
        return "registration-success";
    }
}
