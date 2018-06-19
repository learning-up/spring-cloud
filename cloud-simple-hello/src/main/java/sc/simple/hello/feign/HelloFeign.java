package sc.simple.hello.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("cloud-simple-service")
public interface HelloFeign {

    @GetMapping("hello/get")
    String get();

}
