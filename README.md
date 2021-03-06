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

## 八、分布式配置中心（高可用与动态刷新）【暂略】

## 九、服务容错保护(Hystrix服务降级)

eureka-server工程：服务注册中心
eureka-client工程：服务提供者

`eureka-consumer-ribbon-hystrix`：复制eureka-consumer-ribbon，改造下：

1、添加依赖：spring-cloud-starter-hystrix

2、在应用主类中使用@EnableCircuitBreaker或@EnableHystrix注解开启Hystrix的使用

3、改造服务消费方式，新增ConsumerService类，然后将在Controller中的逻辑迁移过去。最后，在为具体执行逻辑的函数上增加@HystrixCommand注解来指定服务降级方法

4、启动，访问：http://localhost:2601/consumer。

如果eureka-client启动了，可以正常返回；

如果eureka-client没启动（也可以在eureka-client的接口里面sleep 5秒，但是没看到hystrix的默认超时时间是多少？），eureka-consumer-ribbon-hystrix调不通就会返回com.springcloud.DcController.ConsumerService#fallback的内容，<font color="red">但是没有打印异常日志</font>

## 十、服务容错保护(Hystrix依赖隔离)

@HystrixCommand注解将某个函数包装成了Hystrix命令，这里除了定义`服务降级`之外，Hystrix框架就会自动的为这个函数实现调用的隔离。所以，`依赖隔离`、`服务降级`在使用时候都是一体化实现的。除了依赖隔离、服务降级之外，还有一个重要元素：`断路器`。我们将在下一篇做详细的介绍，这三个重要利器构成了Hystrix实现服务容错保护的强力组合拳。

## 十一、服务容错保护(Hystrix断路器)

这里涉及到断路器的三个重要参数：快照时间窗、请求总数下限、错误百分比下限。

<font color="red">这些参数在哪里配置呢？</font>

## 十二、Hystrix监控面板

将用到下之前实现的几个应用：

>eureka-server：服务注册中心
>eureka-client：服务提供者
>eureka-consumer-ribbon-hystrix：使用ribbon和hystrix实现的服务消费者

在Spring Cloud中构建一个Hystrix Dashboard：

1、创建一个springBoot工程：`hystrix-dashboard`【用于展示eureka-consumer-ribbon-hystrix服务的Hystrix数据】

2、为应用主类加上@EnableHystrixDashboard，启用Hystrix Dashboard功能

3、启动，访问：http://localhost:2701/hystrix，可以看到监控首页

=>输入：http://localhost:2601/hystrix.stream【实现对具体某个服务实例的监控】

=>点击“Monitor Stream”按钮，就能看到2601这个端口的服务监控页面，如果没调用数据展示，可以访问下http://localhost:2601/consumer

## 十三、Hystrix监控数据聚合

我们构建的内容包括：

>eureka-server：服务注册中心
>eureka-client：服务提供者
>eureka-consumer-ribbon-hystrix：使用ribbon和hystrix实现的服务消费者
>eureka-consumer-ribbon-hystrix2：复制的eureka-consumer-ribbon-hystrix，改了下端口号
>hystrix-dashboard：用于展示eureka-consumer-ribbon-hystrix服务的Hystrix数据

引入Turbine来对服务的Hystrix数据进行聚合展示

### 通过HTTP收集聚合

1、创建就springBoot工程：`turbine`

2、添加依赖：spring-cloud-starter-turbine

3、应用主类使用@EnableTurbine注解开启Turbine

4、application.properties加入eureka和turbine的相关配置

5、启动，访问：http://localhost:2701/hystrix，开启对http://localhost:8989/turbine.stream的监控，我们将看到针对服务`eureka-consumer-ribbon-hystrix`的聚合监控数据。

### 通过消息代理收集聚合【暂略】

## 十四、服务网关（基础）

用到的项目：

>eureka-server
>eureka-client
>eureka-consumer

构建服务网关：

1、创建springBoot项目：`api-gateway`（端口：1101）

2、添加依赖：spring-cloud-starter-zuul

3、应用主类使用@EnableZuulProxy注解开启Zuul的功能

4、启动，访问：（api-gateway就具备了默认的服务路由功能）

http://localhost:1101/eureka-client/dc

http://localhost:1101/eureka-consumer/consumer

## 十五、服务网关（路由配置）

路由地址映射配置

## 十六、服务网关（过滤器）

用到的项目：

>eureka-server
>eureka-client

1、创建项目`api-gateway-with-eureka`：复制api-gateway

2、实现自定义过滤器，继承ZuulFilter并重写四个方法

3、在实现了自定义过滤器之后，它并不会直接生效，我们还需要为其创建具体的Bean才能启动该过滤器

4、application.properties：给eureka-client配置路由映射地址为/api-a/**

5、启动，访问：

http://localhost:1102/api-a/dc，返回401错误

http://localhost:1102/api-a/dc?accessToken=token：正确路由到eureka-client的dc接口

## 十七、服务网关（API文档汇总）

需要用到的应用：

> eureka-server

1、创建两个springCloud微服务：`swagger-service-a`和`swagger-service-b`

2、引入eureka的依赖、web模块的依赖以及swagger的依赖（这里使用了我们自己构建的starter）

3、@EnableSwagger2Doc

构建API网关并整合Swagger：

1、创建项目：`swagger-api-gateway`

2、引入swagger的依赖

3、应用主类中配置swagger，实现DocumentationConfig接口

4、启动，测试验证：localhost:11000/swagger-ui.html，可以选择a或者b的文档

【也可以单独访问a和b两个应该系统的swagger页面：

localhost:10010/swagger-ui.html、localhost:10020/swagger-ui.html】

## 十八、消息驱动的微服务（入门）

使用消息中间件RabbitMQ来接收消息并将消息打印到日志中：

1、创建项目`stream-hello`

2、引入依赖：spring-cloud-starter-stream-rabbit

3、创建用于接收来自RabbitMQ消息的消费者SinkReceiver

4、启动【本地把rabbitmq的服务也启动起来】，

mq的web地址：http://localhost:15672

可以看到input...的一个队列=>publish message=>com.springcloud.SinkReceiver#receive就会收到这条消息

#### 编写消费消息的单元测试用例

SinkApplicationTests

先启动stream-hello，再运行单元测试发送mq消息，stream-hello会接收到该条消息

## 十九、消息驱动的微服务（核心概念）

Binder绑定器

发布-订阅模式 Topic（RabbitMQ的Exchange）

消费组

消息分区

## 二十、消息驱动的微服务（消费组）

【同一个分组下，只会有一个实例对其进行消费。】

1、生产者：`stream-group-producer`

启动后，SinkSender会一直发送消息

配置：spring.cloud.stream.bindings.output.destination=greetings

2、消费者：`stream-group-consumer`（复制stream-hello修改）

```
spring.cloud.stream.bindings.input.group=Service-A
spring.cloud.stream.bindings.input.destination=greetings
```

复制stream-group-consumer为`stream-group-consumer2`，配置跟stream-group-consumer一样

```
spring.cloud.stream.bindings.input.group=Service-A
spring.cloud.stream.bindings.input.destination=greetings
```

3、启动，验证：

分别运行上面实现的生产者与消费者，其中消费者我们启动多个实例。通过控制台，我们可以发现每个生产者发出的消息，会被启动的消费者以轮询的方式进行接收和输出。【因为消费者配置的同一个分组Service-A；如果其中一个消费者配置成其他分组Service-B，也会收到消息】

## 二十一、消息驱动的微服务（消息分区）

配置了没生效样，spring.cloud.stream.bindings.output.producer.partitionKeyExpression=payload不知道这个key是什么规则

## 二十二、分布式服务跟踪（入门）