package com.baltan.springboot.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019-01-15 19:55
 */
@Configuration
public class MyRabbitmqConfig {

    /**
     * MessageConverter，用于将消息序列化为JSON字符串放入消息队列
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
