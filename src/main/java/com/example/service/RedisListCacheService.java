package com.example.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RedisListCacheService {

    private ObjectMapper objectMapper;
    private RedisTemplate<String,Object> redisTemplate;
    private ListOperations<String ,Object> listOperations;

    public RedisListCacheService(final RedisTemplate<String, Object> redisTemplate,ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        listOperations = redisTemplate.opsForList();
    }


    @PostConstruct
    public void setUP(){
        listOperations.leftPush("keyXX","Initialized");
        System.out.println(listOperations.rightPop("keyXX"));
    }

    public synchronized Iterable<String> getAllKeysFromCache(String pattern){
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        Iterable<String> redisKeys = redisTemplate.keys(pattern);
        List<String> keysList = new ArrayList<>();
        Iterator<String> iterator = redisKeys.iterator();
        while (iterator.hasNext()){
            keysList.add(iterator.next());
        }
        return keysList;
    }



    public synchronized <T> Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }


    public synchronized <T> Object getValue(String key,Class clazz){
        Object obj = redisTemplate.opsForValue().get(key);
        return objectMapper.convertValue(obj,clazz);
    }


    public void setValue(String key,Object value){
        setValue(key,value,TimeUnit.HOURS,5,false);
    }
    public void setValue(String key, Object value, TimeUnit timeUnit,long timeOut,boolean marshal){
        if (marshal) {
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
            redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        }
        redisTemplate.opsForValue().set(key, value);

        redisTemplate.expire(key,timeOut,timeUnit);
    }


    public void setValue(final String key, final Object value, boolean marshal) {
        if (marshal) {
            redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
            redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        }
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 5, TimeUnit.MINUTES);
    }


    public void setValue(final String key, final Object value, TimeUnit unit, long timeout) {
        setValue(key, value, unit, timeout, false);
    }

}
