package ru.yauroff.kfapplication.kfmailsender.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfmailsender.service.KafkaService;
import ru.yauroff.kfapplication.kfmailsender.service.MailSenderService;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

@Service
public class KafkaServiceImpl implements KafkaService {

    private MailSenderService mailSenderService;

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @Override
    @KafkaListener(topics="userRegistrationTopic")
    public void listener(ConsumerRecord<Long, KfRegistrationUserDto> record) {
        mailSenderService.sendMail(record.key(), record.value());
    }
}
