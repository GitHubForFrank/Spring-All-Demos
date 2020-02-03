# Spring Boot 整合 Mybatis


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-主要依赖">1.2 主要依赖</a><br/>
<a href="#二整合-Mybatis">二、整合 Mybatis</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-配置数据源">2.1 配置数据源</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22--XML-方式">2.2  XML 方式</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-注解方式">2.3 注解方式</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-其他参考">2.4 其他参考</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

- 项目涉及表的建表语句放置在 https://github.com/GitHubForFrank/Spring-All-Demos/tree/master/00-materials/database-scripts 下；

- 关于 Mybatis SQL 的写法提供两种方式：

   **xml 写法**：对应的类为 ProgrammerMapper.java 和 programmerMapper.xml，用 MybatisXmlTest 进行测试；

   **注解写法**：对应的类为 Programmer.java ，用 MybatisAnnotationTest 进行测试。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-mybatis/project-structure.png"/> </div>

### 1.2 主要依赖

```xml
<!-- 单元测试 -->
<dependency>

</dependency>
```

## 二、整合 Mybatis

### 2.1 配置数据源

Spring Boot 2.x 版本默认采用 Hikari 作为数据库连接池，Hikari 是目前 Java 平台性能最好的连接池，性能好于 druid：

```properties
#-----------------------------------------------------------------------------------
#Below is for Logback config
logging.config=classpath:logback-config.xml
logging.level.root=debug
logging.file.name=spring-boot-mybatis-druid
logging.file.path=../logs/app


```

### 2.2  XML 方式

新建 ProgrammerMapper.java 和 programmerMapper.xml，及其测试类：

```java
@Mapper
public interface ProgrammerMapper {

}
```

```xml
<?xml version="1.0"  encoding="utf-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="ProgrammerMapper">

</mapper>
```

测试类：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisXmlTest {

}
```

### 2.3 注解方式

```java
@Mapper
public interface ProgrammerDao {

}
```

测试类：

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisAnnotationTest {
 
}

```

### 2.4 其他参考

mybatis 配置更多说明可以参考settings  http://www.mybatis.org/mybatis-3/zh/configuration.html

spring boot 2.0 默认采用Hikari 作为连接池 Hikari github地址 https://github.com/brettwooldridge/HikariCP

