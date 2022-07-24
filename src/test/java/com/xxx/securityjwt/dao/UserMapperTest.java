package com.xxx.securityjwt.dao;


import com.xxx.securityjwt.common.Constant;
import com.xxx.securityjwt.model.dao.UserMapper;
import com.xxx.securityjwt.model.entity.User;
import com.xxx.securityjwt.model.req.UserReq;
import com.xxx.securityjwt.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisCache;
    @Test
    public void OneTest(){
        User user = userMapper.selectById(2);
        System.out.println(user);
    }

    @Test
    public void PasswordEncoderTest(){
        //对1234进行加密，此方法会对密码进行加盐和MD5处理
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
        //对比加密后的结果是否正确
        boolean matches = passwordEncoder.matches("1234", "$2a$10$.gKaLN/.Zk3gmfOPidiGIOiD2U4s2ILnSg6FaqCdvoGEHtLqs7Xn6");
        System.out.println(matches);
    }
    @Test
    public void redisChacheTEst(){
        UserReq userReq = new UserReq();
        userReq.setUserName("user");
        userReq.setPassword("1234");
        String key = Constant.LOGIN_KEY_PRE+userReq.getUserName();
        redisCache.setCacheObject(key,userReq);
        UserReq cacheObject = redisCache.getCacheObject("login:2");
        System.out.println(cacheObject);
    }
    @Test
    public void selectPermissionByUserId(){
        List<String> list = userMapper.selectPermsByUserId(2l);
        System.out.println(list);
    }


}