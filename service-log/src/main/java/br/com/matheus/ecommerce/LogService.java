package br.com.matheus.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class LogService {

    public static void main(String[] args) {
        var logService = new LogService();
        try (var service = new KafkaService<>(
                LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                logService::parse,
                String.class,
                Map.of(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName()))) {
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, String> record) {
        log.info("-".repeat(60));
        log.info("Key -> " + (record.key()));
        log.info("Value -> " + record.value());
        log.info("Partitions -> " + record.partition());
        log.info("Offset -> " + record.offset());
        log.info("LOG -> " + record.topic());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
    }
}
