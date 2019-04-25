package com.springcloud;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yaoyao on 2019/4/25.
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String test() {
        return "springboot测试页面";
    }

}
