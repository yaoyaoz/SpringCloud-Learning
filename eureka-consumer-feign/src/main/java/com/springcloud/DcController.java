package com.springcloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
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
    DcClient dcClient;

    //消费eureka-client提供的接口
    @GetMapping("/consumer")
    public String dc() {
        return dcClient.consumer();
    }

}
