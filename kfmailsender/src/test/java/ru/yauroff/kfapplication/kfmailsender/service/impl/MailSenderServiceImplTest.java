package ru.yauroff.kfapplication.kfmailsender.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;
import ru.yauroff.kfapplication.kfmailsender.service.MailSenderService;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailSenderServiceImplTest {
    @Mock
    private JavaMailSender mailSenderMock;
    private MailSenderService instance;

    @Value("${spring.mail.not.send}")
    private boolean notSendMail;
    @Value("${spring.mail.username}")
    private String mailSendFrom;

    @BeforeEach
    public void before() {
        this.instance = new MailSenderServiceImpl(mailSenderMock);
    }

    @Test
    public void whenSendMailIsTriggered_thenSendMail() {
        // when
        Long userId = 1L;
        KfRegistrationUserDto kfRegistrationUserDto = new KfRegistrationUserDto("fName", "lName", "login", "email");
        this.instance.sendMail(userId, kfRegistrationUserDto);

        // then
        ArgumentCaptor<SimpleMailMessage> argument = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(this.mailSenderMock, timeout(100).times(1)).send(argument.capture());
    }

}