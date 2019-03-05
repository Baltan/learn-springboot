package com.baltan.springboot.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Description: 自定义健康状态指示器
 *
 * @author Baltan
 * @date 2019-01-25 22:56
 */
@Component
public class MyApplicationHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {

        /**
         * flag值可以用一个自定义检查方法来返回
         */
        boolean flag = false;

        if (flag) {
            return Health.up().build();
        } else {
            return Health.down().withDetail("msg", "服务异常！").build();
        }
    }
}
