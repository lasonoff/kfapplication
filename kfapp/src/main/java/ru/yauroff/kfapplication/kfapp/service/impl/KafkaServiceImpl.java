package ru.yauroff.kfapplication.kfapp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import ru.yauroff.kfapplication.kfapp.service.KafkaService;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {
    private KafkaTemplate<Long, KfRegistrationUserDto> kafkaTemplate;

    @Value("${kafka.topic}")
    private String kafkaTopic;


    @Autowired
    public void setKafkaTemplate(KafkaTemplate<Long, KfRegistrationUserDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void flushUser(Long msgId, KfRegistrationUserDto kfRegistrationUserDto) {
        ListenableFuture<SendResult<Long, KfRegistrationUserDto>> future = kafkaTemplate.send(kafkaTopic, msgId, kfRegistrationUserDto);
        future.addCallback((info) -> log.info(String.valueOf(info)), (error) -> log.error(String.valueOf(error)));
        kafkaTemplate.flush();
    }
}
