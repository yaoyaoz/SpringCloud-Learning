package com.springcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.core.MessageSource;
import org.springframework.messaging.support.GenericMessage;

import java.util.Date;

/**
 * @Description: 消息生产者
 * @author: yaoyao
 * @date 2020-02-25
 */
@EnableBinding(value = {Source.class})
public class SinkSender {

    private static Logger logger = LoggerFactory.getLogger(SinkSender.class);

    /*
    fixedDelay = "5000"：5秒发送一次消息
     */
    @Bean
    @InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "5000"))
    public MessageSource<Long> timerMessageSource() {
        logger.info("开始发送消息...");
        return () -> new GenericMessage<>(new Date().getTime());
    }

}