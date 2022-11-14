package ru.yauroff.kfapplication.kfmailsender.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;
import ru.yauroff.kfapplication.kfmailsender.service.KafkaService;
import ru.yauroff.kfapplication.kfmailsender.service.MailSenderService;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final MailSenderService mailSenderService;

    @Override
    @KafkaListener(topics = "userRegistrationTopic")
    public void listener(ConsumerRecord<Long, KfRegistrationUserDto> record) {
        mailSenderService.sendMail(record.key(), record.value());
    }
}
