package ru.yauroff.kfapplication.kfmailsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.EnableKafka;


@SpringBootApplication
@EnableKafka
@Slf4j
public class KfMailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(KfMailSenderApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {
        log.info(this.getClass() + " ready to work!");
    }
}
