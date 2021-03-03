package br.com.matheus.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.regex.Pattern;

@Slf4j
public class LogService {

    public static void main(String[] args) {
        var emailService = new LogService();
        try (var service = new KafkaService(
                LogService.class.getSimpleName(),
                Pattern.compile("ECOMMERCE.*"),
                emailService::parse)) {
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
