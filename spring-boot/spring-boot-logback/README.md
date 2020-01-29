# Spring Boot 整合 Logback


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-主要依赖">1.2 主要依赖</a><br/>
<a href="#二整合-Mybatis 实现动态数据源">二、整合 Mybatis 实现动态数据源</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-配置 application.properties 和 logback-config.xml">2.1 配置 application.properties 和 logback-config.xml</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-单元测试观测log">2.2 单元测试观测log</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-Web调用观测log">2.3 Web调用观测log</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-logback/project-structure.png"/> </div>

### 1.2 主要依赖

```xml
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
	<java.version>1.8</java.version>
	<logback.version>1.2.3</logback.version>
</properties>

<dependencies>
	<!-- 单元测试 -->
	<dependency>
		<groupId>junit</groupId>
		<artifactId>junit</artifactId>
		<version>4.12</version>
		<scope>test</scope>
	</dependency>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-test</artifactId>
		<scope>test</scope>
	</dependency>

	<!-- Spring Boot 配置-->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>

	<!-- Logback 依赖 -->
	<dependency>
		<groupId>ch.qos.logback</groupId>
		<artifactId>logback-core</artifactId>
		<version>${logback.version}</version>
	</dependency>
	<dependency>
		<groupId>ch.qos.logback</groupId>
		<artifactId>logback-classic</artifactId>
		<version>${logback.version}</version>
	</dependency>

	<!-- 其他依赖 -->
	<dependency>
		<groupId>org.projectlombok</groupId>
		<artifactId>lombok</artifactId>
	</dependency>
</dependencies>
```


## 二、整合 Logback 实现记录log

### 2.1 配置 application.properties 和 logback-config.xml

如下是 tomcat server 、 logback 的相关配置：

```properties
#-----------------------------------------------------------------------------------
#如下配置将会在 logback-config.xml 中用到，可以用来区分不同的环境，例如开发环境和生产环境
spring.profiles.active = dev

#-----------------------------------------------------------------------------------
#Below is for Tomcat Server config
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
logging.file.name=spring-boot-logback
logging.file.path=../logs/app
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>

    <!-- 可以Spring boot 在application中配置logback xml参数-->
    <springProperty scope="context" name="log.path" source="logging.file.path"/>
    <springProperty scope="context" name="log.name" source="logging.file.name"/>
    <springProperty scope="context" name="log.level.root" source="logging.level.root"/>

    <!-- 控制台输出 -->
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!--格式化输出：%d表示日期，%thread表示线程名，%-5level：级别从左显示5个字符宽度%msg：日志消息，%n是换行符-->
            <pattern>[%date{yyyy-MM-dd HH:mm:ss.SSS}]-[%r]-[%t]-[%p]-[%logger] %.-10000m%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>debug</level>
        </filter>
    </appender>
    <appender name="ASYNC_CONSOLE" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <queueSize>10000</queueSize>
        <appender-ref ref="CONSOLE" />
    </appender>

    <!-- 按照每天生成日志文件 -->
    <appender name="BASE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${log.path}/${log.name}.log</file>
        <encoder>
            <pattern>[%date{yyyy-MM-dd HH:mm:ss.SSS}]-[%r]-[%t]-[%p]-[%logger] %m%n</pattern>
            <charset>UTF-8</charset>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${log.path}/history/${log.name}-%d{yyyy-MM-dd}-%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>50MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
    </appender>
    <appender name="ASYNC_BASE" class="ch.qos.logback.classic.AsyncAppender">
        <discardingThreshold>0</discardingThreshold>
        <queueSize>10000</queueSize>
        <appender-ref ref="BASE" />
    </appender>

    <!-- dev(开发环境)开启console台输出log，同时将log打印到文件-->
    <springProfile name="dev">
        <!-- 日志输出级别 TRACE, DEBUG, INFO, WARN, ERROR, ALL 和 OFF -->
        <root level="${log.level.root}">
            <appender-ref ref="ASYNC_CONSOLE"/>
            <appender-ref ref="ASYNC_BASE"/>
        </root>
    </springProfile>

    <!-- sit(系统测试环境)，uat(User测试环境)，prod(生产环境)的时候将log打印到文件-->
    <springProfile name="sit,uat,prod">
        <root level="${log.level.root}">
            <appender-ref ref="ASYNC_BASE"/>
        </root>
    </springProfile>

</configuration>

```


### 2.2 单元测试观测log

测试类： 

```java
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void test_query() {
        log.debug("==========================================");
        List<UserModel> list = userService.queryAllUser();
        log.info("test_query 查询结果:{}",list.size());
        log.debug("==========================================");
    }

}
```


### 2.3 Web调用观测log

Controller类： 

```java
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping(value = "queryAll")
    public UserDto queryAllUser() {
        log.info("开始执行queryAllUser");
        UserDto userDto = new UserDto();
        userDto.setModelList(userService.queryAllUser());
        return userDto;
    }

}
```

URL : `http://localhost:10190/app/user/queryAll`


