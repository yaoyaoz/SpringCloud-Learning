package com.springcloud;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: yaoyao
 * @date 2020-02-24
 */
@EnableSwagger2Doc //开启Swagger功能的注解
@EnableZuulProxy
@SpringCloudApplication
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).web(true).run(args);
    }

    @Component
    @Primary
    class DocumentationConfig implements SwaggerResourcesProvider {
        @Override
        public List<SwaggerResource> get() {
            /*
            网关上Swagger会通过访问/swagger-service-a/v2/api-docs和swagger-service-b/v2/api-docs来加载两个文档内容，
            同时由于当前应用是Zuul构建的API网关，这两个请求会被转发到swagger-service-a和swagger-service-b服务上的/v2/api-docs接口获得到Swagger的JSON文档，从而实现汇总加载内容。
             */
            List resources = new ArrayList();
            resources.add(swaggerResource("service-a", "/swagger-service-a/v2/api-docs", "2.0"));
            resources.add(swaggerResource("service-b", "/swagger-service-b/v2/api-docs", "2.0"));
            return resources;
        }

        private SwaggerResource swaggerResource(String name, String location, String version) {
            SwaggerResource swaggerResource = new SwaggerResource();
            swaggerResource.setName(name);
            swaggerResource.setLocation(location);
            swaggerResource.setSwaggerVersion(version);
            return swaggerResource;
        }

    }

}