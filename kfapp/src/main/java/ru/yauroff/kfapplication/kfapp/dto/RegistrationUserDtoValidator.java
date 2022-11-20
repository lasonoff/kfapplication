package ru.yauroff.kfapplication.kfapp.dto;

public class RegistrationUserDtoValidator {
    public boolean validate(RegistrationUserDto registrationUserDto) {
        return registrationUserDto != null && registrationUserDto.getLogin() != null
                && registrationUserDto.getPassword() != null && registrationUserDto.getEmail() != null;
    }
}
