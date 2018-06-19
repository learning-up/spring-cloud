package sc.simple.hello.feign;

import org.springframework.stereotype.Component;

@Component
public class HelloRemoteHystrix implements HelloFeign{

    @Override
    public String get() {
        return "hello, this messge send failed ";
    }

}
