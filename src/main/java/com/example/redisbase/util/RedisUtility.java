package com.example.redisbase.util;

import com.example.redisbase.dto.AuthUserDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtility {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    Gson gson;

    public void setValue(final String key, AuthUserDTO authUserDTO){
        redisTemplate.opsForValue().set(key,gson.toJson(authUserDTO));
        redisTemplate.expire(key,10, TimeUnit.MINUTES);
    }
    public AuthUserDTO getValue(final String key){
        return gson.fromJson(redisTemplate.opsForValue().get(key),AuthUserDTO.class);
    }
    public void deleteKeyFromRedis(String key){
        redisTemplate.delete(key);
    }
}
