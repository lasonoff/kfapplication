package ru.yauroff.kfapplication.kfapp.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import ru.yauroff.kfapplication.kfapp.service.KafkaService;
import ru.yauroff.kfapplication.kfdto.KfRegistrationUserDto;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaServiceImplTest {
    private KafkaService instance;
    @Value("${kafka.topic}")
    private String kafkaTopic;

    @Mock
    private KafkaTemplate<Long, KfRegistrationUserDto> kafkaTemplateMock;
    @Mock
    private ListenableFuture<SendResult<Long, KfRegistrationUserDto>> listenableFuture;

    @BeforeEach
    public void before() {
        this.instance = new KafkaServiceImpl(kafkaTemplateMock);
    }

    @Test
    public void whenFlushUserTriggered_thenSendAndFlushTriggered() {
        //when
        Long id = 1L;
        KfRegistrationUserDto kfRegistrationUserDto = new KfRegistrationUserDto();
        when(kafkaTemplateMock.send(kafkaTopic, id, kfRegistrationUserDto))
                .thenReturn(this.listenableFuture);
        this.instance.flushUser(id, kfRegistrationUserDto);
        //then
        ArgumentCaptor<Long> argumentId = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> argumentTopic = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<KfRegistrationUserDto> argumentUserDto = ArgumentCaptor.forClass(KfRegistrationUserDto.class);
        verify(this.kafkaTemplateMock).send(argumentTopic.capture(), argumentId.capture(), argumentUserDto.capture());
    }
}