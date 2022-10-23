package ru.yauroff.kfapplication.kfapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.yauroff.kfapplication.kfapp.model.User;

import java.util.Map;

@Data
@AllArgsConstructor
public class RegistrationUserDto {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String email;

    public static RegistrationUserDto fromParamsMap(Map<String, String> registrationParam) {
        return new RegistrationUserDto(registrationParam.get("firstname"), registrationParam.get("lastname"), registrationParam.get("login"), registrationParam.get("password"),
                registrationParam.get("email"));
    }

    public User toUser() {
        User user = new User();
        user.setFirstname(getFirstname());
        user.setLastname(getLastname());
        user.setLogin(getLogin());
        user.setPassword(getPassword());
        user.setEmail(getEmail());
        return user;
    }
}
