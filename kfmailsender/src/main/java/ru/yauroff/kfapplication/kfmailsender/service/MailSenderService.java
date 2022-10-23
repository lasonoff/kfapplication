package ru.yauroff.kfapplication.kfmailsender.service;

import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

@Service
public interface MailSenderService {
    void sendMail(Long userId, KfRegistrationUserDto kfRegistrationUserDto);
}
