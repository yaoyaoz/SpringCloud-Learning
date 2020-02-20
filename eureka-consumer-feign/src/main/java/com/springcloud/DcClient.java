package com.springcloud;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description: Feign的客户端接口
 * @author: yaoyao
 * @date 2020-02-20
 */
@FeignClient("eureka-client") //指定这个接口所要调用的服务名称
public interface DcClient {

    //接口中定义的各个函数使用Spring MVC的注解就可以来绑定服务提供方的REST接口，比如下面就是绑定eureka-client服务的/dc接口
    @GetMapping("/dc")
    String consumer();

}