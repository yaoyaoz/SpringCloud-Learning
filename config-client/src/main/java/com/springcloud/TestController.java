package com.springcloud;

import com.springcloud.test.DemoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: yaoyao
 * @date 2020-02-20
 */
@RestController
public class TestController {

    @GetMapping("getInfo")
    public String getInfo() {
        DemoConfiguration demoConfiguration = new DemoConfiguration();
        return demoConfiguration.getInfo();
    }

}