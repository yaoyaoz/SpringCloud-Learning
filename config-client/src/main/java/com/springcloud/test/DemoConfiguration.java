package com.springcloud.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author: yaoyao
 * @date 2020-02-20
 */
@Configuration
@EnableAutoConfiguration
public class DemoConfiguration {

    @Value("${info}")
    private String info;

    public DemoConfiguration() {
        System.out.println("DemoConfiguration构造函数");
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}