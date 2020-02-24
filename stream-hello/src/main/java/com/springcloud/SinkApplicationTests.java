package com.springcloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description: 消费消息的单元测试用例
 * @author: yaoyao
 * @date 2020-02-24
 */
@RunWith(SpringRunner.class)
@EnableBinding(value = SinkApplicationTests.SinkSender.class)
public class SinkApplicationTests {

    @Autowired
    private SinkSender sinkSender;

    @Test
    public void sindSenderTester() {
        sinkSender.output().send(MessageBuilder.withPayload("send a message: stream-hello").build());
    }

    public interface SinkSender {
        String OUTPUT = "input";

        @Output(SinkSender.OUTPUT)
        MessageChannel output();
    }

}