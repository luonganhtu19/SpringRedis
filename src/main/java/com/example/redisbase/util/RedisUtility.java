package com.example.redisbase.util;

import com.example.redisbase.dto.AuthUserDTO;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public void saveHash(final String key, final String hashKey, String hashValue){
        HashOperations<String,String,String> hashOps=  redisTemplate.opsForHash();
        hashOps.put(key, hashKey, hashValue);
    }

    public String getHash(String key, String hashKey){
        HashOperations<String,String,String>  hashOps = redisTemplate.opsForHash();
        return hashOps.get(key,hashKey);
    }

    public void deleteHash(String key, String hashKey){
        HashOperations<String,String,String> hashOps = redisTemplate.opsForHash();
        hashOps.delete(key, hashKey);
    }

    public void redisSetGetExample(String key, String value){
        redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException{
                connection.set(key.getBytes(),value.getBytes());
                return null;
            }
        });

        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] valueBytes= redisConnection.get(key.getBytes());
                return new String(valueBytes);
            }
        });
        System.out.println("Redis value");
    }

    public void setKeyCallBack(String key, String value){
        redisTemplate.execute(new RedisCallback<Void>() {
            public Void doInRedis(RedisConnection redisConnection) throws DataAccessException{
                redisConnection.set(key.getBytes(),value.getBytes());
                return null;
            }
        });
    }

    public void usingTransaction(){
        String key1 = "key1";
        String key2 = "key2";
        String key3 = "key3";
        String value1 = "key1";
        String value2 = "key2";
        String value3 = "key3";
        redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public  List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.multi();
                redisOperations.opsForValue().set(key1,value1);
                redisOperations.opsForValue().set(key2,value2);
                return redisOperations.exec();
            }
        });
    }
}
