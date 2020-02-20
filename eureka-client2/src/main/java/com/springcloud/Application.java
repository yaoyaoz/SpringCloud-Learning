package com.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by yaoyao on 2019/4/25.
 */
@EnableDiscoveryClient //激活Eureka中的DiscoveryClient实现，这样才能实现Controller中对服务信息的输出
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
