# 《Spring Cloud构建微服务架构》系列入门教程【Dalston版】————程序猿DD

## 一、服务注册与发现（Eureka、Consul）

### Spring Cloud Eureka:

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

### Spring Cloud Consul

consul-client：以eureka-client为基础

1、把eureka的依赖改为spring-cloud-starter-consul-discovery

2、application.properites改成consul的配置

3、下载consul的服务端=>解压=>配置环境变量：【G:\develop\consul_1.7.0_windows_amd64】

4、启动Application，访问：localhost:3001/dc：【consul-Service:[consul, consul-client]】

## 二、服务消费者（基础）

利用上一篇中构建的eureka-server作为服务注册中心、eureka-client作为服务提供者作为基础

1、创建服务消费者：

eureka-consumer

启动Application后，访问：localhost:2101/consumer：【Service:[eureka-client, eureka-consumer]】

其中，【】中的内容是调用eureka-client的接口localhost:2001/dc返回的内容

## 三、服务消费者（Ribbon）





