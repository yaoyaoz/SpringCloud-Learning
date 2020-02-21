package com.springcloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 服务消费
 *
 * Created by yaoyao on 2020-02-19.
 */
@RestController
public class DcController {

    @Autowired
    ConsumerService consumerService;

    //消费eureka-client提供的接口
        @GetMapping("/consumer")
    public String dc() {
        return consumerService.consumer();
    }

    @Service
    class ConsumerService {
        @Autowired
        RestTemplate restTemplate;

        @HystrixCommand(fallbackMethod = "fallback")
        public String consumer() {
            return "eureka-consumer-ribbon-hystrix2：" + restTemplate.getForObject("http://eureka-client/dc", String.class);
        }

        public String fallback() {
            return "服务调用异常：hystrix 服务降级。fallback";
        }
    }

}
