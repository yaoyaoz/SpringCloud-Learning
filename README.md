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

`eureka-consumer-feign`：基于eureka-consumer

1）、添加依赖spring-cloud-starter-feign

2）、修改应用主类。通过@EnableFeignClients注解开启扫描Spring Cloud Feign客户端的功能

3）、创建一个Feign的客户端接口定义：DcClient

4）、修改Controller。通过定义的feign客户端来调用服务提供方的接口

5）、启动，访问：localhost:2301/consumer

也可以通过启动多个eureka-client服务来观察其负载均衡的效果：eureka-client2

## 五、服务消费者（Feign）传文件

利用之前构建的`eureka-server`作为服务注册中心

服务提供方（接收文件）：`eureka-feign-upload-server`

服务消费方（发送文件）：`eureka-feign-upload-client` 测试用例：com.springcloud.UploadTester#testHandleFileUpload

## 六、分布式配置中心



基于Git仓库的配置中心：`config-server-git`
使用配置中心的客户端：`config-client`

### 1）准备配置仓库

https://github.com/yaoyaoz/SpringCloud-config-repo-demo

假设我们读取配置中心的应用名为config-client，那么我们可以在git仓库中该项目的默认配置文件`config-client.yml`；

为了演示加载不同环境的配置，我们可以在git仓库中再创建一个针对dev环境的配置文件`config-client-dev.yml`

### 2）构建配置中心

1、创建一个基础的springBoot工程：`config-server-git`，并添加依赖spring-cloud-config-server

2、主类添加`@EnableConfigServer`注解，开启Spring Cloud Config的服务端功能

3、在application.yml中添加配置服务的基本信息以及Git仓库的相关信息

4、启动，访问：http://localhost:1201/config-client/dev/master【/{application}/{profile}[/{label}]，映射{application}-{profile}.properties对应的配置文件，其中{label}对应Git上不同的分支，默认为master】，获得如下返回：

![config获取git配置文件参数](/images/001-config获取git配置文件参数.png "config获取git配置文件参数")

### 3）构建客户端

1、创建工程`config-client`，依赖spring-cloud-starter-config

2、启动config-server-git和config-client，访问localhost:1202/info，<font color="red">但是没返回git仓库的配置信息</font>

## 七、分布式配置中心（加密与解密）【略】

## 八、分布式配置中心（高可用与动态刷新）【略】

## 九、服务容错保护(Hystrix服务降级)





