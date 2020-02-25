package com.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @Description: 消费者：接收来自RabbitMQ的消息
 * @author: yaoyao
 * @date 2020-02-24
 */
@EnableBinding(Sink.class) //该注解用来指定一个或多个定义了@Input或@Output注解的接口，以此实现对消息通道（Channel）的绑定
public class SinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(SinkReceiver.class);

    //该注解主要定义在方法上，作用是将被修饰的方法注册为消息中间件上数据流的事件监听器，注解中的属性值对应了监听的消息通道名。
    @StreamListener(Sink.INPUT)
    public void receive(Object payload) {
        logger.info("Received: {}", payload);
    }

}