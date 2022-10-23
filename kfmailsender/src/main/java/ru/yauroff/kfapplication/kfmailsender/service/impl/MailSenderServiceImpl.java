package ru.yauroff.kfapplication.kfmailsender.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;
import ru.yauroff.kfapplication.kfmailsender.service.MailSenderService;

@Service
@Slf4j
public class MailSenderServiceImpl implements MailSenderService {
    @Value("${spring.mail.send}")
    private boolean sendMail;

    @Value("${spring.mail.username}")
    private String mailSendFrom;

    private JavaMailSender mailSender;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender emailSender) {
        this.mailSender = emailSender;
    }

    public void sendMail(Long userId, KfRegistrationUserDto kfRegistrationUserDto) {
        if (!sendMail) {
            writeMailToLog(kfRegistrationUserDto);
        } else {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailSendFrom);
            message.setTo(kfRegistrationUserDto.getEmail());
            message.setSubject("Registration!");
            message.setText("Hi, " + kfRegistrationUserDto.getFirstname() + "!\n\n"
                    + "You are registered on web service with login: " + kfRegistrationUserDto.getLogin() + ".");
            mailSender.send(message);
        }
    }

    private void writeMailToLog(KfRegistrationUserDto kfRegistrationUserDto) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
        log.info("Not send mail for user with e-mail: " + kfRegistrationUserDto.getEmail());
    }
}
