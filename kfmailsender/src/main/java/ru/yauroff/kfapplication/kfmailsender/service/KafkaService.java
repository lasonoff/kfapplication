package ru.yauroff.kfapplication.kfmailsender.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

@Service
public interface KafkaService {
    void listener(ConsumerRecord<Long, KfRegistrationUserDto> record);
}
