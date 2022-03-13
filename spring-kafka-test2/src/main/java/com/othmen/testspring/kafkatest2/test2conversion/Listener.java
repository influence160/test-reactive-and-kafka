package com.othmen.testspring.kafkatest2.test2conversion;

import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    @KafkaListener(id = "listen1", topics = "invoicer-documentCheck-out")
    public void listen1(String in) {
        System.out.println(in);
    }

}