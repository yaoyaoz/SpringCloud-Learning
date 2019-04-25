《Spring Cloud构建微服务架构》系列入门教程【Dalston版】————程序猿DD


一、服务注册与发现（Eureka、Consul）
Spring Cloud Eureka:
1、创建“服务注册中心”：
    eureka-server
    @EnableEurekaServer
    启动Application后，访问localhost:1001，还没有发现任何服务
2、创建“服务提供方”：
    eureka-client
    org.springframework.cloud.client.discovery.DiscoveryClient
    @EnableDiscoveryClient
    启动Application后，再次访问：localhost:1001/，可以看到我们定义的服务被注册上去了。
    访问localhost:2001/dc：Services: [eureka-client]
    其中，方括号中的eureka-client就是通过Spring Cloud定义的DiscoveryClient接口在eureka的实现中获取到的所有服务清单

Spring Cloud Consul
看到这里，http://blog.didispace.com/spring-cloud-starter-dalston-1/