package br.com.matheus.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

@Slf4j
public class EmailService {

    public static void main(String[] args) {
        var emailService = new EmailService();
        var service = new KafkaService(EmailService.class.getSimpleName(), "ECOMMERCE_SEND_EMAIL", emailService::parse);
        service.run();
    }

    private void parse(ConsumerRecord<String, String> record) {
        log.info("-".repeat(60));
        log.info("Send email...");
        log.info("Key -> " + (record.key()));
        log.info("Value -> " + record.value());
        log.info("Partitions -> " + record.partition());
        log.info("Offset -> " + record.offset());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
        log.info("Email sent with success!");
    }
}