package cloud.kafka.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Customer {

    @KafkaListener(topics = {"test"})
    public void processMessage(ConsumerRecord<?, ?> record) {
        log.info("get data {} {}", record.key(), record.value());
    }

}
