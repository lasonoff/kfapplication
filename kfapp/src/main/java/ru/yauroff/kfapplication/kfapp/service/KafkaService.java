package ru.yauroff.kfapplication.kfapp.service;

import org.springframework.stereotype.Service;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

@Service
public interface KafkaService {

    void flushUser(Long msgId, KfRegistrationUserDto kfRegistrationUserDto);
}
