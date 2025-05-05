package com.pfms.pfms_analytics.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;


    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String key, String message) {
        ProducerRecord<String, String> kafkaRecord = new ProducerRecord<>(
                topic,
                null,
                key,
                message,
                List.of(
                        new RecordHeader("source", "spring-boot-app".getBytes(StandardCharsets.UTF_8)),
                        new RecordHeader("eventType", "demoEvent".getBytes(StandardCharsets.UTF_8))
                )
        );

        kafkaTemplate.send(kafkaRecord);

    }
}
