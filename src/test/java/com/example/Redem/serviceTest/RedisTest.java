package com.example.Redem.serviceTest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    void redisTest(){
        redisTemplate.opsForValue().set("email" , "test123@gmail.com");
        Object email = redisTemplate.opsForValue().get("email");
    }

}