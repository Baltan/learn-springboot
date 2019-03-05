package com.baltan.springboot.config;

import com.baltan.springboot.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019/1/10 17:20
 */
@Configuration
public class MyRedisConfig {

    /**
     * 自定义RedisTemplate，用于将User类序列化为JSON字符串缓存
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, User> UserRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, User> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        template.setDefaultSerializer(serializer);

        return template;
    }
}
