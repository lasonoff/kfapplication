package ru.yauroff.kfapplication.kfapp.kfdtoconverters;

import ru.yauroff.kfapplication.kfapp.model.User;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

public class KfRegistrationUserDtoConverter {
    public static KfRegistrationUserDto fromUser(User user) {
        KfRegistrationUserDto kfRegistrationUserDto = new KfRegistrationUserDto(user.getFirstname(), user.getLastname(), user.getLogin(), user.getEmail());
        return kfRegistrationUserDto;
    }
}
