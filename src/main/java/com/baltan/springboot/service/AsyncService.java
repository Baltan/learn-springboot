package com.baltan.springboot.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019-01-18 15:45
 */
@Component
public class AsyncService {

    @Async
    public void greet() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("处理数据中……");
    }
}
