package ru.yauroff.kfapplication.kfdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KfRegistrationUserDto {
    private String firstname;
    private String lastname;
    private String login;
    private String email;
}
