package cloud.kafka.sample.controller;

import cloud.kafka.sample.service.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class TestKafka {

    @Autowired private Producer producer;

    @RequestMapping("/send/{key}/{data}")
    @ResponseBody
    public String send(@PathVariable("key") String key, @PathVariable("data") String data) {
        producer.send("test", key, data);
        return "success";
    }

}
