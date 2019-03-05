package com.baltan.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 为了打包springboot项目,需要在启动类当中加上继承SpringBootServletInitializer类并重写configure方法
 * <p>
 * 项目启动准备：
 * 1、进入/usr/local/etc目录，执行redis-server my-redis.conf启动Redis服务
 * 2、进入/usr/local/Cellar/rabbitmq/3.7.10目录，执行sbin/rabbitmq-server -detached命令后台启动RabbitMQ服务
 * 3、在根目录下执行brew services start elasticsearch命令启动ElasticSearch服务
 */
@SpringBootApplication
@MapperScan("com.baltan.springboot.mapper")
@EnableCaching
@EnableRabbit
@EnableAsync
@EnableScheduling
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        /**
         * 不加这句话项目启动会报Error creating bean with name 'elasticsearchClient'
         */
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(getClass());
    }
}

