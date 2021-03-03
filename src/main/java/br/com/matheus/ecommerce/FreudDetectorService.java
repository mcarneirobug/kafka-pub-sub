package br.com.matheus.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

@Slf4j
public class FreudDetectorService {

    public static void main(String[] args) {
        var freudService = new FreudDetectorService();
        try (var service = new KafkaService<>(
                EmailService.class.getSimpleName(),
                "ECOMMERCE_NEW_ORDER",
                freudService::parse,
                Order.class,
                Map.of())) { // é obrigatório, porém podemos passar vazio
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) {
        log.info("-".repeat(60));
        log.info("Processing new order, checking for freud!");
        log.info("Key ->  " + (record.key()));
        log.info("Value ->  " + record.value());
        log.info("Partitions -> " + record.partition());
        log.info("Offset -> " + record.offset());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.error("Interrupted!", e);
            Thread.currentThread().interrupt();
        }
        log.info("Order processed with success");
    }
}
