package ru.yauroff.kfapplication.kfapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KfAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(KfAppApplication.class, args);
    }

}
