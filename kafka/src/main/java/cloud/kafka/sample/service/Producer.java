package cloud.kafka.sample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class Producer {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void send(String topic, String key, String data) {
        kafkaTemplate.send(topic, key, data);
        log.info("send {} {}", key, data);

//        kafkaTemplate.metrics();

//        kafkaTemplate.execute(new KafkaOperations.ProducerCallback<String, String, Object>() {
//            @Override
//            public Object doInKafka(Producer<String, String> producer) {
//                //这里可以编写kafka原生的api操作
//                return null;
//            }
//        });
//
//        //消息发送的监听器，用于回调返回信息
//        kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
//            @Override
//            public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
//
//            }
//
//            @Override
//            public void onError(String topic, Integer partition, String key, String value, Exception exception) {
//
//            }
//
//            @Override
//            public boolean isInterestedInSuccess() {
//                return false;
//            }
//        });
    }

}
