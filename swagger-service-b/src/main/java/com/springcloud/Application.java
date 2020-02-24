package com.springcloud;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: yaoyao
 * @date 2020-02-24
 */
@EnableSwagger2Doc //自制Swagger Starter中提供的自定义注解，通过该注解会初始化默认的Swagger文档设置。
@EnableDiscoveryClient
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @RestController
    class BbbController {
        @Autowired
        DiscoveryClient discoveryClient;

        @GetMapping("/service-b")
        public String dc() {
            String services = "service-b-Services: " + discoveryClient.getServices();
            System.out.println(services);
            return services;
        }
    }

}