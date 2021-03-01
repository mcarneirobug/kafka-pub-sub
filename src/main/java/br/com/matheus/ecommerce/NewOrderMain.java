package br.com.matheus.ecommerce;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
public class NewOrderMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var producer = new KafkaProducer<String, String>(properties())) { /* Tipo da chave e o tipo da mensagem */
            var value = "132323,67534,79794729472";
            var record = new ProducerRecord<>("ECCOMERCE_NEW_ORDER", value, value);
            /* send é um método assíncrono (future) utilizaremos o get para esperar ele terminar */
            producer.send(record, (data, ex) -> {
                if (ex != null) {
                    log.error(ex.getMessage());
                    return;
                }
                log.info("Success send topic: " + data.topic() + " - with partition: " + data.partition()
                        + " - with offset: " + data.offset() + " - with timestamp: " + data.timestamp());
            }).get();
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092"); /* especificar aonde está rodando o kafka */
        /*
          * Tanto a chave quanto a mensagem irão transformar em strings,
          * ou seja, iremos passar serializadores de string para bytes.
         */
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;
    }
}
