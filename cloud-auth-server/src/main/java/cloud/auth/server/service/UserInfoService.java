package cloud.auth.server.service;

import cloud.auth.server.model.UserInfo;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

    public UserInfo findByName(String username) {
        //TODO 该处只是为了模拟查询数据库
        return new UserInfo("test", "test");
    }
}
