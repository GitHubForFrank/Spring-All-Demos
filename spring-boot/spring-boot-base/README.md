# Spring-Boot 基础


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-基本依赖">1.2 基本依赖</a><br/>
<a href="#二主启动类">二、主启动类</a><br/>
<a href="#三开箱即用">三、开箱即用</a><br/>
<a href="#四模板引擎">四、模板引擎</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#41-freemarker">4.1 freemarker</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#42-thymeleaf">4.2 thymeleaf</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#43-文档说明">4.3 文档说明</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

1. 本项目搭建一个简单的 hello spring 的 Web 工程，简单说明 Spring-Boot 的开箱即用的特性；
2. 模板引擎采用 freemaker 和 thymeleaf 作为示例，分别对应模板文件 makershow.ftl 和 leafShow.html；
3. Spring Boot 2.x 默认不支持 Jsp ，需要额外的配置，关于使用 jsp 的整合可以参考 [spring-boot-jsp](https://github.com/GitHubForFrank/Spring-All-Demos/tree/master/spring-boot/spring-boot-jsp) 项目。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-base/project-structure.png"/> </div>

### 1.2 基本依赖

导入相关的 starter (启动器)：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.2.2.RELEASE</version>
        <relativePath /> <!-- lookup parent from repository -->
    </parent>

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.zmz</groupId>
    <artifactId>spring-boot-base</artifactId>
    <version>0.1-SNAPSHOT</version>
    <name>spring-boot-base</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <logback.version>1.2.3</logback.version>
    </properties>

    <dependencies>
        <!--模板引擎-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!--web 启动器-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--lombok 插件-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--测试相关依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <!--Spring Boot的Maven插件（Spring Boot Maven plugin）能够以Maven的方式为应用提供Spring Boot的支持-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <mainClass>com.zmz.app.SpringBootBaseApplication</mainClass>
                    <includeSystemScope>true</includeSystemScope>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```

+ Spring Boot 项目默认继承自 spring-boot-starter-parent，而 spring-boot-starter-parent 则继承自 spring-boot-dependencies，spring-boot-dependencies 中定义了关于 spring boot 依赖的各种 jar 包的版本，它是 Spring Boot 的版本管理中心。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-base/spring-boot-dependencies.png"/> </div>

+ 关于Spring Boot 2.x 官方支持的所有 starter 可以参见官方文档：[Table 13.1. Spring Boot application starters](https://docs.spring.io/spring-boot/docs/2.2.2.RELEASE/reference/htmlsingle/#using-boot-starter)



## 二、主启动类

 如果采用 IDEA 或者 Spring Tool Suite (STS)  等开发工具创建的 Spring Boot 工程，会默认创建启动类，如果没有创建，需要手动创建启动类：

```java
/**
 * 默认开启包扫描，扫描与主程序所在包及其子包，对于本工程而言 默认扫描 本类所在的package
 * @author ASNPHDG
 * @create 2020-01-27 15:15
 */
@Slf4j
@ComponentScan(basePackages = {"com.zmz"})
@SpringBootApplication
public class SpringBootBaseApplication {

    public static void main(String[] args) {
        log.debug("Application.main.begin");
        SpringApplication app = new SpringApplication(SpringBootBaseApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
        log.info("Application.main.completed");
    }

}
```

@SpringBootApplication 是一个复合注解，里面包含了 @ComponentScan 注解，即默认开启包扫描，扫描主程序所在包及其子包，对于本工程而言，默认扫描 com.zmz.app：

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = {
		@Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
    ...
}
```



## 三、开箱即用

采用 Spring Boot 构建的 Web 项目具备开箱即用的特性，不需要做任何额外的配置就可以正常使用。这里我们在 springbootBaseApplication 的同级目录创建 controller 文件夹，并在其中创建 RestfulController 控制器.

```java
@RestController
@RequestMapping("restful")
public class RestfulController {

    @GetMapping("programmers")
    private List<Programmer> getProgrammers() {
        List<Programmer> programmers = new ArrayList<>();
        programmers.add(new Programmer("restful-xiaoming", 12, 100000.00f, LocalDate.of(2019, Month.AUGUST, 2)));
        programmers.add(new Programmer("restful-xiaohong", 23, 900000.00f, LocalDate.of(2013, Month.FEBRUARY, 2)));
        return programmers;
    }
}
```

```url
http://localhost:10190/app/restful/programmers
```

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-base/restful.png"/> </div>

Spring Boot 之所以能够开箱即用，是因为我们在项目中导入 spring-boot-starter-web 启动器，而 @SpringBootApplication 复合注解中默认开启了 @EnableAutoConfiguration ，即允许开启自动化配置。 Spring Boot 检查到存在 starter-web 依赖后就会开启 Web 相关的自动化配置。


## 四、模板引擎

这里我们在一个项目中同时导入了 freemaker 和 thymeleaf 的 starter（虽然并不推荐，但是在同一个项目中是可以混用这两种模板引擎的）:

### 4.1 freemarker

```java
/**
 * @author ASNPHDG
 * @create 2020-01-27 15:15
 * @description : 跳转渲染模板引擎 默认模板的存放位置为classpath:templates
 */
@Controller
@RequestMapping("freemarker")
public class FreeMarkerController {

    @RequestMapping("show")
    private String programmerShow(ModelMap modelMap){
        List<Programmer> programmerList=new ArrayList<>();
        programmerList.add(new Programmer("freemarker-xiaoming",12,100000.00f,LocalDate.of(2019,Month.AUGUST,2)));
        programmerList.add(new Programmer("freemarker-xiaohong",23,900000.00f,LocalDate.of(2013,Month.FEBRUARY,2)));
        modelMap.addAttribute("programmers",programmerList);
        return "markerShow";
    }
    
}

```

```html
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>freemarker 模板引擎</title>
</head>
<body>
    <ul>
        <#list programmers as programmer>
           <li>姓名: ${programmer.name} 年龄: ${programmer.age}</li>
        </#list>
    </ul>
</body>
</html>
```

```url
http://localhost:10190/app/freemarker/show
```

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-base/freemarker.png"/> </div>

### 4.2 thymeleaf

```java
/**
 * @author ASNPHDG
 * @create 2020-01-27 15:15
 * @description : 跳转渲染模板引擎 默认模板的存放位置为classpath:templates
 */
@Controller
@RequestMapping("thymeleaf")
public class ThymeleafController {

    @RequestMapping("show")
    private String programmerShow(ModelMap modelMap) {
        List<Programmer> programmerList = new ArrayList<>();
        programmerList.add(new Programmer("thymeleaf-xiaoming", 12, 100000.00f, LocalDate.of(2019, Month.AUGUST, 2)));
        programmerList.add(new Programmer("thymeleaf-xiaohong", 23, 900000.00f, LocalDate.of(2013, Month.FEBRUARY, 2)));
        modelMap.addAttribute("programmers", programmerList);
        return "leafShow";
    }
    
}
```

```html
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>thymeleaf 模板引擎</title>
</head>
<body>
    <ul th:each="programmer:${programmers}">
        <li>
            姓名:<span th:text="${programmer.name}"></span>
            薪水:<span th:text="${programmer.salary}"></span>
        </li>
    </ul>
</body>
</html>
```


```url
http://localhost:10190/app/thymeleaf/show
```

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-base/thymeleaf.png"/> </div>

### 4.3 文档说明

关于这两种模板引擎的语法可以参考其官方文档：

- **freemarker**：提供了完善的中文文档，地址为： http://freemarker.foofun.cn/ 。
- **thymeleaf**：官方英文文档地址：[thymeleaf 3.0.11RELEASE](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.pdf) 。我在本仓库中上传了一份 [thymeleaf 中文文档（gangzi828(刘明刚 译）](https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/documents)，翻译的版本为 3.0.5RELEASE。



