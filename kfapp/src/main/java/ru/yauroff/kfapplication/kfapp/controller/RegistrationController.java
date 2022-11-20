package ru.yauroff.kfapplication.kfapp.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yauroff.kfapplication.kfapp.dto.RegistrationUserDto;
import ru.yauroff.kfapplication.kfapp.dto.RegistrationUserDtoValidator;
import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfapp.service.UserService;
import ru.yauroff.kfapplication.kfapp.service.exceptions.UserRegistrationException;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final RegistrationUserDtoValidator registrationUserDtoValidator = new RegistrationUserDtoValidator();

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String registerNewUser(@RequestParam Map<String, String> registrationParams, ModelMap model) {
        RegistrationUserDto registrationUser = RegistrationUserDto.fromParamsMap(registrationParams);
        if (registrationUserDtoValidator.validate(registrationUser)) {
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
            user = userService.registerNewUser(userForRegistration);
            log.info(user.toString());
        } catch (UserRegistrationException e) {
            model.put("errorText", e.getMessage());
            return "registration";
        }
        model.put("user", userForRegistration);
        return "registration-success";
    }

}
