package com.baltan.springboot.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019/1/9 16:20
 */
@Configuration
public class MyCacheConfig {

    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> method.getName() + "[" + Arrays.asList(objects).toString() + "]";
    }
}
