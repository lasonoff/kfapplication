package ru.yauroff.kfapplication.kfmailsender.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;
import ru.yauroff.kfapplication.kfmailsender.service.MailSenderService;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailSenderServiceImpl implements MailSenderService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.not.send}")
    private boolean notSendMail;
    @Value("${spring.mail.username}")
    private String mailSendFrom;

    public void sendMail(Long userId, KfRegistrationUserDto kfRegistrationUserDto) {
        if (notSendMail) {
            log.info("Not send mail for user with e-mail: " + kfRegistrationUserDto.getEmail());
        } else {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSendFrom);
            message.setTo(kfRegistrationUserDto.getEmail());
            message.setSubject("Registration!");
            message.setText("Hi, " + kfRegistrationUserDto.getFirstname() + "!\n\n"
                    + "You are registered on web service with login: " + kfRegistrationUserDto.getLogin() + ".");
            //TODO:
            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> mailSender.send(message))
                                                                         .exceptionally(ex -> {
                                                                             log.error(ex.getMessage());
                                                                             return null;
                                                                         });
        }
    }
}
