# Spring Boot Actuator


<nav>
<a href="#一相关概念">一、相关概念</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-执行端点">1.1 执行端点</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-启用端点">1.2 启用端点</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#13-暴露端点">1.3 暴露端点</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#14-健康检查">1.4 健康检查</a><br/>
<a href="#二项目说明">二、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-项目结构">2.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-主要依赖">2.2 主要依赖</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-项目配置">2.3 项目配置</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-监控状态">2.4 监控状态</a><br/>
<a href="#三自定义健康检查指标和查看Beans">三、自定义健康检查指标和查看Beans</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#31-自定义健康检查指标">3.1 自定义健康检查指标</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#32-查看Beans">3.2 查看Beans</a><br/>
<a href="#四自定义端点">四、自定义端点</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#41-Hyperic-Sigar">4.1 Hyperic Sigar </a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#42-自定义端点">4.2 自定义端点</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#43-访问自定义端点">4.3 访问自定义端点</a><br/>
</nav>

## 一、相关概念

### 1.1 执行端点

Spring  Boot 提供了很多执行器端点（endpoints）用于监控应用的运行情况以及与应用进行交互，并支持将这些端点按需暴露给外部使用。 端点暴露的方式取决于你采用的技术类型，通常可以端点的 ID 映射到一个 URL，从而可以将端口暴露为 HTTP 服务。例如，将health 端点默认映射到 /health。Spring Boot 内置的常用端点如下：

| ID          | 描述                                                         | 是否敏感 |
| ----------- | ------------------------------------------------------------ | -------- |
| actuator    | 为其他端点提供基于超文本的导航页面，需要添加 Spring HATEOAS 依赖 | true     |
| autoconfig  | 显示一个自动配置类的报告，该报告展示所有自动配置候选者及它们被应用或未被应用的原因 | true     |
| beans       | 显示一个应用中所有 Spring Beans 的完整列表                   | true     |
| configprops | 显示一个所有 @ConfigurationProperties 的集合列表             | true     |
| dump        | 执行一个线程转储                                             | true     |
| env         | 暴露来自 Spring ConfigurableEnvironment 的属性               | true     |
| flyway      | 显示数据库迁移路径，如果有的话                               | true     |
| health      | 展示应用的健康信息（当使用一个未认证连接访问时显示一个简单的 'status'，使用认证连接访问则显示全部信息详情） | false    |
| info        | 显示任意的应用信息                                           | false    |
| liquibase   | 展示任何 Liquibase 数据库迁移路径，如果有的话                | true     |
| metrics     | 展示当前应用的 'metrics' 信息                                | true     |
| mappings    | 显示一个所有 @RequestMapping 路径的集合列表                  | true     |
| shutdown    | 允许应用以优雅的方式关闭（默认情况下不启用）                 | true     |
| trace       | 显示 trace 信息（默认为最新的 100 条 HTTP 请求）             | true     |

如果使用了 Spring MVC，还有以下额外的端点：

| ID       | 描述                                                         | 是否敏感 |
| -------- | ------------------------------------------------------------ | -------- |
| docs     | 展示 Actuator 的文档，包括示例请求和响应，需添加 spring-boot-actuator-docs 依赖 | false    |
| heapdump | 返回一个 GZip 压缩的 hprof 堆转储文件                            | true     |
| jolokia  | 通过 HTTP 暴露 JMX beans（依赖 Jolokia）                         | true     |
| logfile  | 返回日志文件内容（如果设置 logging.file 或 logging.path 属性），支持使用 HTTP Range 头接收日志文件内容的部分信息 |          |

端点按照安全属性可以分为敏感和非敏感两类，在启用 Web 安全服务后，访问敏感端点时需要提供用户名和密码，如果没有启用 web 安全服务，Spring Boot 可能会直接禁止访问该端点。



### 1.2 启用端点

默认情况下，除了 shutdown 以外的所有端点都已启用。端点的启停可以使用 management.endpoint.\<id>.enabled 属性来进行控制，示例如下：

```properties
management.endpoint.shutdown.enabled = true
```



### 1.3 暴露端点

由于端点可能包含敏感信息，因此应仔细考虑后再决定是否公开。下表显示了内置端点的默认公开情况：

| ID             | JMX   | Web  |
| -------------- | ----- | ---- |
| auditevents    | 是    | 没有 |
| beans          | 是    | 没有 |
| conditions     | 是    | 没有 |
| configprops    | 是    | 没有 |
| env            | 是    | 没有 |
| flyway         | 是    | 没有 |
| health         | 是    | 是   |
| heapdump       | N / A | 没有 |
| httptrace      | 是    | 没有 |
| info           | 是    | 是   |
| jolokia        | N / A | 没有 |
| logfile        | N / A | 没有 |
| loggers        | 是    | 没有 |
| liquibase      | 是    | 没有 |
| metrics        | 是    | 没有 |
| mappings       | 是    | 没有 |
| prometheus     | N / A | 没有 |
| scheduledtasks | 是    | 没有 |
| sessions       | 是    | 没有 |
| shutdown       | 是    | 没有 |
| threaddump     | 是    | 没有 |

可以选择是否暴露端点（include）或者排除端点（exclude）,其中排除属性优先于暴露属性：

| 属性                                      | 默认         |
| ----------------------------------------- | ------------ |
| management.endpoints.jmx.exposure.exclude |              |
| management.endpoints.jmx.exposure.include | *            |
| management.endpoints.web.exposure.exclude |              |
| management.endpoints.web.exposure.include | info, health |



### 1.4 健康检查

health 端点用于暴露程序运行的健康状态，暴露的信息的详细程度由 management.endpoint.health.show-details 来控制，它具有以下三个可选值：

| 名称            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| never           | 细节永远不会显示。                                           |
| when-authorized | 详细信息仅向授权用户显示。授权角色可以使用配置 management.endpoint.health.roles。 |
| always          | 详细信息显示给所有用户。                                     |



## 二、项目说明

### 2.1 项目结构

- **CustomHealthIndicator** 自定义健康指标；
- **CustomEndPoint**：自定义端点。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/project-structure.png"/> </div>


### 2.2 主要依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

### 2.3 项目配置

```properties
#-----------------------------------------------------------------------------------
#Below is for Server and Tomcat config
server.port=10190
server.servlet.context-path=/app
server.tomcat.max-threads=800
server.tomcat.uri-encoding=UTF-8
server.tomcat.basedir=../logs/tomcat
server.tomcat.accesslog.enabled=true
server.tomcat.accesslog.pattern=%t %a "%r" %s (%D ms)
server.tomcat.accesslog.directory=access

#-----------------------------------------------------------------------------------
#Below is for Logback config
logging.config=classpath:logback-config.xml
logging.level.root=debug
logging.file.name=spring-boot-actuator
logging.file.path=../logs/app

#-----------------------------------------------------------------------------------
#Below is for security config
# 若要访问端点信息，需要配置用户名和密码
spring.security.user.name=admin
spring.security.user.password=123456
## 端点信息接口使用的端口，为了和主系统接口使用的端口进行分离
management.server.port=10191
management.server.servlet.context-path=/sys
# 端点健康情况，默认值"never"，设置为"always"可以显示硬盘使用情况和线程情况
management.endpoint.health.show-details=always
# 设置端点暴露的哪些内容，默认["health","info"]，设置"*"代表暴露所有可访问的端点
management.endpoints.web.exposure.include=*

```

### 2.4 监控状态

导入 Actuator 的 starter 并进行配置后，访问 `http://127.0.0.1:10191/sys/actuator/health` 就可以看到对应的项目监控状态。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/health.png"/> </div>

健康指标 HealthIndicators 由 Spring Boot 自动配置，因此这里显示监控信息是由项目所使用的技术栈而决定的：


| 名称                                                         | 描述                             |
| ------------------------------------------------------------ | -------------------------------- |
| [CassandraHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/cassandra/CassandraHealthIndicator.java) | 检查 Cassandra 数据库是否启动。    |
| [DiskSpaceHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/system/DiskSpaceHealthIndicator.java) | 检查磁盘空间不足。               |
| [DataSourceHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/jdbc/DataSourceHealthIndicator.java) | 检查是否可以获得连接 DataSource。 |
| [ElasticsearchHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/elasticsearch/ElasticsearchHealthIndicator.java) | 检查 Elasticsearch 集群是否启动。  |
| [InfluxDbHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/influx/InfluxDbHealthIndicator.java) | 检查 InfluxDB 服务器是否启动。     |
| [JmsHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/jms/JmsHealthIndicator.java) | 检查 JMS 代理是否启动。            |
| [MailHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/mail/MailHealthIndicator.java) | 检查邮件服务器是否启动。         |
| [MongoHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/mongo/MongoHealthIndicator.java) | 检查 Mongo 数据库是否启动。        |
| [Neo4jHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/neo4j/Neo4jHealthIndicator.java) | 检查 Neo4j 服务器是否启动。        |
| [RabbitHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/amqp/RabbitHealthIndicator.java) | 检查 Rabbit 服务器是否启动。       |
| [RedisHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/redis/RedisHealthIndicator.java) | 检查 Redis 服务器是否启动。        |
| [SolrHealthIndicator](https://github.com/spring-projects/spring-boot/tree/v2.2.2.RELEASE/spring-boot-project/spring-boot-actuator/src/main/java/org/springframework/boot/actuate/solr/SolrHealthIndicator.java) | 检查 Solr 服务器是否已启动。       |



## 三、自定义健康检查指标和查看Beans

### 3.1 自定义健康检查指标
```java
@Component
public class CustomHealthIndicator extends AbstractHealthIndicator{
    @Override
    protected void doHealthCheck(Health.Builder builder) {
        // 这里用随机数模拟健康检查的结果
        double random = Math.random();
        //使用构建器构建应报告的运行状况详细信息。
        //如果引发异常，则状态为DOWN且显示异常消息。
        if (random > 0.5) {
            builder.up().status("FATAL").withDetail("error code", "某健康专项检查失败");
            int s=1/0;
        } else {
            builder.up().withDetail("success code", "自定义检查一切正常");
        }
    }
}

```

自定义检查通过的情况下：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/health.png"/> </div>

自定义检查失败的情况：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/health-fail.png"/> </div>

自定义检查不论是否通过都不会影响整体的 status，因此两种情况下的 status 值都是 `up`。如果想通过自定义检查去影响整体的检查结果，比如健康检查针对的是支付业务，在支付业务的不可用的情况下，我们就应该认为整个服务是不可用的，这个时候就需要通过自定义健康状态的聚合规则来实现。

### 3.2 查看Beans

如下图可以看到自定义的UserController：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/health-bean.png"/> </div>


## 四、自定义端点

### 4.1 Hyperic Sigar 

Spring Boot 支持使用 `@Endpoint` 来自定义端点暴露信息，这里以暴露服务所在硬件的监控信息为例。

想要获取服务器信息需要通过第三方工具来实现，Sigar 是 Hyperic HQ 下的数据收集组件，其底层采用 C 语言进行编写，它通过本地方法调用操作系统的 API 来获取系统相关数据 ，其 JAR 包的下载地址为：https://sourceforge.net/projects/sigar/  。

Sigar 为不同平台提供了不同的库文件，下载后需要将库文件放到服务所在主机的对应位置：

- **Windows** ：根据操作系统版本选择 sigar-amd64-winnt.dll 或 sigar-x86-winnt.dll 并拷贝到 C:\Windows\System32 下；
- **Linux**：将 libsigar-amd64-linux.so 或 libsigar-x86-linux.so 拷贝以下任意目录：`/usr/lib64` ， `/lib64` ，`/lib` ， `/usr/lib` ，如果不起作用，还需要通过 `sudo chmod 744` 命令修改 libsigar-amd64-linux.so 的文件权限。

考虑到和系统有关，暂时不提供Sigar的实现。

### 4.2 自定义端点

```java
@Endpoint(id = "customEndPoint")
@Component
public class CustomEndPoint {
    /**
     * 可以自定义端点获取想要的系统信息
     * @return
     */
    @ReadOperation
    public Map<String, Object> getCustomDefineInfo() {
        Map<String, Object> cupInfoMap = new LinkedHashMap<>();
        cupInfoMap.put("key01","value01");
        cupInfoMap.put("key02","value02");
        cupInfoMap.put("key03","value03");
        return cupInfoMap;
    }
}
```

可用的方法注解由 HTTP 操作所决定：

| operation        | HTTP 方法 |
| ---------------- | -------- |
| @ReadOperation   | GET      |
| @WriteOperation  | POST     |
| @DeleteOperation | DELETE   |

### 4.3 访问自定义端点

地址为：`http://127.0.0.1:10191/sys/actuator/customEndPoint` ：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/customEndPoint.png"/> </div>

关于 Sigar 的更多监控参数可以参考博客：[java 读取计算机 CPU、内存等信息（Sigar 使用）](https://blog.csdn.net/wudiazu/article/details/73829324) 或 Sigar 下载包中的用例：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-actuator/sigar.png"/> </div>
