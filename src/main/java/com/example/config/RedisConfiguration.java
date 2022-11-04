package com.example.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {


    @Bean
    public LettuceConnectionFactory connectionFactory(){
        final RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);
        return new LettuceConnectionFactory(configuration);
    }


    @Bean
    public RedisTemplate<String,Object> redisTemplate(){
        final RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return redisTemplate;
    }
}

