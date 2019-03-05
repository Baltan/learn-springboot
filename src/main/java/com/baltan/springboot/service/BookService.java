package com.baltan.springboot.service;

import com.baltan.springboot.bean.Book;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Baltan
 * @date 2019-01-15 21:51
 */
@Component
public class BookService {

    @RabbitListener(queues = {"atguigu.news"})
    public void getBook(Book book) {
        System.out.println("收到消息： " + book);
    }

    @RabbitListener(queues = {"gulixueyuan.news"})
    public void getMessage(Message message) {
        System.out.println(message.getBody());
        System.out.println(message.getMessageProperties());
    }
}
