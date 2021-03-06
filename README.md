# Spring-All-Demos

![spring](https://img.shields.io/badge/spring-5.2.2.RELEASE-brightgreen.svg)    ![springboot](https://img.shields.io/badge/springboot-2.2.2.RELEASE-brightgreen.svg)    ![springcloud](https://img.shields.io/badge/springcloud-Finchley.SR2-brightgreen.svg)    ![jdk](https://img.shields.io/badge/jdk->=1.8-blue.svg)    ![author](https://img.shields.io/badge/author-frank-orange.svg)


本项目仓库提供Spring框架的的常用整合用例，包括单框架的使用，各个模块无依赖，均可单独运行。

**特别说明**：

- 项目涉及表的建表语句放置在 https://github.com/GitHubForFrank/Spring-All-Demos/tree/master/00-materials/database-scripts 文件夹下；

- 项目中目前用到的数据库有SqlServer和MySql，本人在百度云服务器已经创建的对应数据库，已经做了内网穿透，公网即可访问。至于百度云服务器到期日待定。如果想要创建表，希望自己本地搭建数据库，此处共享具有对表数据的CRUD权限的账号密码：

&nbsp;&nbsp;&nbsp;&nbsp;服务器地址：182.61.149.71<br/>
&nbsp;&nbsp;&nbsp;&nbsp;用户名：kong<br/>
&nbsp;&nbsp;&nbsp;&nbsp;密码：2020Jan11


**版本说明**：

spring： 5.2.2.RELEASE

spring-boot：2.2.2.RELEASE

spring-cloud：Finchley.SR2
<br/>

## 1. Spring Demos

所有 spring 的项目主要提供基于注解的演示。

| Demo Name                                                     | 描述                                                         | 官方文档                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| [springmvc](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring/springmvc) | springmvc 基础、参数绑定、格式转换、数据校验、异常处理、 文件上传下载、视图渲染 | [Spring Mvc ](https://docs.spring.io/spring/docs/5.2.2.RELEASE/spring-framework-reference/web.html#mvc) |


<br/>

## 2. Spring-boot Demos

| Demo Name                                                      | 描述                                       | 官方文档                                                     |
| ------------------------------------------------------------ | ------------------------------------------ | ------------------------------------------------------------ |
| [spring-boot-actuator](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-actuator) | actuator + Hyperic SIGAR 应用信息监控| [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#production-ready) |
| [spring-boot-base](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-base) | spring-boot 基础| [spring boot 官方文档](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/)<br>[spring boot 中文官方文档](https://www.breakyizhan.com/springboot/3028.html) |
| [spring-boot-data-jpa](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-data-jpa) | spring-boot data jpa 的使用| [Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.2.2.RELEASE/reference/html/) |
| [spring-boot-dubbo](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-dubbo) | spring-boot 整合 dubbo| [Dubbo ](http://dubbo.apache.org/zh-cn/docs/user/quick-start.html) |
| [spring-boot-jsp](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-jsp) | spring-boot 整合 jsp（内置容器）| [JSP Limitations](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-jsp-limitations) |
| [spring-boot-kafka](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-kafka) | spring-boot 整合 kafka| [Apache Kafka Support](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-kafka) |
| [spring-boot-logback](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-logback) | spring-boot 整合 logback | [spring boot 官方文档](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) |
| [spring-boot-memcached](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-memcached) | spring-boot 整合 memcached| [Xmemcached](https://github.com/killme2008/xmemcached/wiki/Xmemcached%20%E4%B8%AD%E6%96%87%E7%94%A8%E6%88%B7%E6%8C%87%E5%8D%97) |
| [spring-boot-mybatis](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-mybatis) | spring-boot+HikariDataSources 整合 mybatis | [Mybatis-Spring](http://www.mybatis.org/spring/zh/index.html)<br/>[Mybatis-Spring-Boot-Autoconfigure](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/) |
| [spring-boot-mybatis-druid](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-mybatis-druid) | spring-boot+DruidDataSource 整合 mybatis| [Alibaba druid](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)<br/>[druid-spring-boot-starter](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter) |
| [spring-boot-mybatis-druid-atomikos](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-mybatis-druid-atomikos) | spring-boot & mybatis 实现动态数据源,并且实现分布式事务 | [Alibaba druid](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)<br/>[druid-spring-boot-starter](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter) |
| [spring-boot-mybatis-druid-multiple-datasource](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-mybatis-druid-multiple-datasource) | spring-boot & mybatis & druid 实现多数据源| [Alibaba druid](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)<br/>[druid-spring-boot-starter](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter) |
| [spring-boot-mybatis-dynamic-datasource](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-mybatis-dynamic-datasource) | spring-boot 整合 mybatis 实现动态数据源<br/>同时支持DruidDataSource和HikariDataSource | [Mybatis-Spring](http://www.mybatis.org/spring/zh/index.html)<br/>[Mybatis-Spring-Boot-Autoconfigure](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/) |
| [spring-boot-rabbitmq](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-rabbitmq) | spring-boot 整合 rabbitmq| [RabbitMQ support](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-rabbitmq) |
| [spring-boot-redis](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-redis) | spring-boot 整合 redis | [Working with NoSQL Technologies](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#boot-features-nosql) |
| [spring-boot-swagger2](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-swagger2) | spring-boot 集成 Swagger2 打造在线接口文档| [Springfox Reference Documentation](http://springfox.github.io/springfox/docs/current/) |
| [spring-boot-websocket](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-websocket) | spring-boot 整合 websocket| [Using @ServerEndpoint](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#howto-create-websocket-endpoints-using-serverendpoint) |
| [spring-boot-yml-profile](https://github.com/GitHubForFrank/spring-all-demos/tree/master/spring-boot/spring-boot-yml-profile) | yml 语法和多配置切换| [Springfox Reference Documentation](http://springfox.github.io/springfox/docs/current/) |

<br/>

## 3. Spring-cloud  Demos

| Demo Name                                                      | 描述                                                         | 官方文档                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |




## Github 常用按钮说明

- Watch：关注该项目，作者有更新的时候，会在你的 Github 主页有通知消息。
- Star：收藏该项目，在你的头像上有一个 “Your stars” 链接，可以看到你的收藏列表，以方便下次进来。
- Fork：复制一份项目到自己的 Github 空间上，你可以自己开发自己的这个地址项目，然后 Pull Request 给项目原主人。 
- 只 clone 最新的一个版本记录，历史旧数据不 clone 的两种方法（推荐这样做，因为图片很多，占了很大空间）：
	- 命令行方法：`git clone https://github.com/GitHubForFrank/spring-all-demos.git --depth=1`
	- TortoiseGit GUI 方法：

![clone 一个版本](00-materials/images/clone-depth-1.png)

