package com.baltan.springboot;

import com.baltan.springboot.bean.Article;
import com.baltan.springboot.bean.Book;
import com.baltan.springboot.bean.User;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedisTemplate<Object, User> userRedisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private JestClient jestClient;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Test
    public void contextLoads() {
    }

    @Test
    public void StringRedisTest() {
        stringRedisTemplate.opsForValue().set("k1", "v1");

        System.out.println(stringRedisTemplate.opsForValue().get("k1"));
    }

    @Test
    public void redisTest() {
        User user = new User("zhangsan", 20, "zhangsan", "zhangsan");
        /**
         * 缓存的对象需要实现Serializable接口
         */
        redisTemplate.opsForValue().set("zhangsan", user);

        System.out.println(redisTemplate.opsForValue().get("zhangsan"));
    }

    @Test
    public void userRedisTest() {
        User user = new User("lisi", 20, "lisi", "lisi");
        userRedisTemplate.opsForValue().set("lisi", user);

        System.out.println(userRedisTemplate.opsForValue().get("lisi"));
    }

    /**
     * 消息发往单播模式（点对点）交换器（direct类型）
     */
    @Test
    public void directTypeSendMessageTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", Arrays.asList(1, 2, 3, 4, 5));
        map.put("k3", new User("zhangsan", 20, "zhangsan", "zhangsan"));

        //        rabbitTemplate.send(exchange,routingKey,message);
        rabbitTemplate.convertAndSend("exchange.direct", "atguigu.news", map);
    }

    /**
     * 消息发往广播模式交换器（fanout类型）
     */
    @Test
    public void fanoutTypeTest() {
        Map<String, Object> map = new HashMap<>();
        map.put("k1", "v1");
        map.put("k2", Arrays.asList(1, 2, 3, 4, 5));
        map.put("k3", new User("zhangsan", 20, "zhangsan", "zhangsan"));

        //        rabbitTemplate.send(exchange,routingKey,message);
        rabbitTemplate.convertAndSend("exchange.fanout", "", map);
    }

    /**
     * 消息发往topic类型交换器
     */
    @Test
    public void topicTypeTest() {
        Book book = new Book("白夜行", "东野圭吾");

        //        rabbitTemplate.send(exchange,routingKey,message);
        rabbitTemplate.convertAndSend("exchange.topic", "fuck.news", book);
    }

    /**
     * 从消息队列中获取一条消息
     */
    @Test
    public void receiveMessageTest() {
        Object message = rabbitTemplate.receiveAndConvert("atguigu.news");
        System.out.println(message.getClass());
        System.out.println(message);
    }

    /**
     * 创建交换器
     */
    @Test
    public void createExchange() {
        amqpAdmin.declareExchange(new DirectExchange("amqpadmin.exchange"));
        System.out.println("交换器创建完成……");
    }

    /**
     * 创建消息队列
     */
    @Test
    public void createQueue() {
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));
        System.out.println("消息队列创建完成……");
    }

    /**
     * 创建交换器和消息队列的绑定规则
     */
    @Test
    public void createBinding() {
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", Binding.DestinationType.QUEUE, "amqpadmin" +
                ".exchange", "amqpadmin.routingkey", null));
    }

    /**
     * 用Jest给ElasticSearch中索引（保存）一个文档
     */
    @Test
    public void jestIndexTest() {
        Article article = new Article(1, "罗贯中", "三国演义", "我从未见过如此厚颜无耻之人……");

        /**
         * 构建一个索i引功能
         */
        Index index = new Index.Builder(article).index("bookstore").type("novel").build();
        try {
            jestClient.execute(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用Jest从ElasticSearch搜索
     */
    @Test
    public void jestSearchTest() {
        String json = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"author\" : \"罗贯中\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        Search search = new Search.Builder(json).addIndex("bookstore").addType("novel").build();
        try {
            SearchResult result = jestClient.execute(search);
            System.out.println(result.getJsonString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送简单邮件
     */
    @Test
    public void sendMail1() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("邮件标题");
        simpleMailMessage.setText("邮件内容");
        simpleMailMessage.setTo("bli1991@163.com");
        simpleMailMessage.setFrom("617640006@qq.com");
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送复杂邮件
     */
    @Test
    public void sendMail2() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setSubject("邮件标题");
        mimeMessageHelper.setText("<em style='color:red'>周末愉快！</em>", true);
        mimeMessageHelper.addAttachment("1.jpg", new File("/Users/Baltan/Desktop/1.jpg"));
        mimeMessageHelper.addAttachment("2.jpg", new File("/Users/Baltan/Desktop/2.jpg"));
        mimeMessageHelper.setTo("bli1991@163.com");
        mimeMessageHelper.setFrom("617640006@qq.com");
        javaMailSender.send(mimeMessage);
    }
}

