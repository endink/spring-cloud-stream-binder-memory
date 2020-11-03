# Spring Cloud Stream Binder Memory

使用内存队列作为中间件 的 spring cloud stream 标准实现.

![Hex.pm](https://img.shields.io/hexpm/l/plug.svg)
![Maven Central](https://img.shields.io/maven-central/v/com.labijie/spring-cloud-stream-binder-memory.svg?color=orange)


## 为什么需要这个项目，应用场景是什么？

1. 贫穷架构：单进程单点部署的程序，因为贫穷的原因，可以节省昂贵的中间件开销。
2. 演示类场景： 对于 DEMO, 演示程序，项目早期的项目程序，无需中间件也可以使用 spring cloud stream 。
3. 平滑迁移：无需更改任何代码，仅改变一行配置就可以平滑过度到真正的 "cloud stream" 模式。
4. 本机开发：代码拿回家，更改一行代码即可本机调试（当然需要你的 Producer 和 Consumer 能够在同一个进程启动）

## 限制：

Producer 和 Consumer 在同一进程内

> 如果所有的项目都制作成 spring boot starter （我厂的最佳实践就是这样）, 这当然不是问题！

# Quick Start

**添加依赖**

```groovy
dependencies {
    implementation "com.labijie:spring-cloud-stream-binder-memory:1.1.0"
}
```


### 使用 spring cloud stream

参考文档：https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/

** application.yml 中添加配置 **

```yaml
spring:
  cloud:
    stream:
      default-binder: memory
      bindings:
        input:
          destination: sms
        output:
          destination: sms
```

> spring cloud stream 的标准配置模式都支持，这，是一个标准实现！
       

### 可选配置

```yaml
spring:
  cloud:
    stream:
      memory:
        binder:
          worker-pool-size: -1
          queue-size: 2048
```

说明：

|配置         | 默认值           | 说明  |
| ------------- |:-------------:| -----:|
|spring.cloud.stream.memory.binder.worker-pool-size| -1 | 工作线程池大小，当小于 0 时使用 CPU 核心数 1 半 | 
|spring.cloud.stream.memory.binder.queue-size| 2048 | 阻塞队列大小，当队列满了以后将丢弃最早的消息 | 

> 所有的配置都能够在 IDEA 中智能提示

### 开发环境兼容性：
 
 |组件|版本|说明|
 |--------|--------|--------|
 |   kotlin    |      1.4.10    |           |
 |   jdk    |      1.8   |           |
 |   spring boot    |      2.3.4.RELEASE    |           |
 |  spring cloud    |      Hoxton.SR8    |   通过 BOM 控制版本，因为 cloud 组件版本混乱，无法统一指定  |
 |   spring framework    |      5.2.9.RELEASE   |           |
 |   spring dpendency management    |      1.0.10.RELEASE    |         

### 祝你使用愉快 ！