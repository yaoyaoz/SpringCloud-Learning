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

利用之前构建的`eureka-server`作为服务注册中心、`eureka-client`作为服务提供者作为基础。

基于Spring Cloud Ribbon实现的消费者，我们可以根据eureka-consumer实现的内容进行简单改造就能完成。

1、创建服务消费者工程：

`eureka-consumer-ribbon`：基于eureka-consumer

1）、添加依赖spring-cloud-starter-ribbon

2）、修改应用主类。为RestTemplate增加@LoadBalanced注解

3）、修改Controller。去掉原来通过`LoadBalancerClient`选取实例和拼接URL的步骤，直接通过RestTemplate发起请求

4）、启动Application，访问：localhost:2201/consumer

也可以通过启动多个eureka-client服务来观察其负载均衡的效果：eureka-client2（复制eureka-client）

## 四、服务消费者（Feign）

利用之前构建的`eureka-server`作为服务注册中心、`eureka-client`作为服务提供者作为基础。

1、创建服务消费者工程：

eureka-consumer-feign：基于eureka-consumer

1）、添加依赖spring-cloud-starter-feign

2）、修改应用主类。通过@EnableFeignClients注解开启扫描Spring Cloud Feign客户端的功能

3）、创建一个Feign的客户端接口定义：DcClient

4）、修改Controller。通过定义的feign客户端来调用服务提供方的接口

5）、启动，访问：localhost:2301/consumer

也可以通过启动多个eureka-client服务来观察其负载均衡的效果：eureka-client2

## 五、服务消费者（Feign）传文件









