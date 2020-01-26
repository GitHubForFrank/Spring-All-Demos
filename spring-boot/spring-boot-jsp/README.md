# Spring Boot 整合 JSP
<nav>
<a href="#一项目说明">一、项目说明</a><br/>
<a href="#二整合-JSP">二、整合 JSP</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-基本依赖">2.1 基本依赖</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-配置视图">2.2 配置视图</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23--整合测试">2.3  整合测试</a><br/>
</nav>

## 一、项目说明

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-jsp/structure.png"/> </div>

## 二、整合 JSP

### 2.1 基本依赖

导入整合所需的依赖：

```xml
<!--整合 jsp 依赖包-->
<dependency>
    <groupId>org.apache.tomcat.embed</groupId>
    <artifactId>tomcat-embed-jasper</artifactId>
    <scope>provided</scope>
</dependency>
<!--jsp jstl 标签支持-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>jstl</artifactId>
</dependency>
```

### 2.2 配置视图

在 application.properties 中指定访问视图文件的前缀和后缀 ：

```properties
#-----------------------------------------------------------------------------------
#Below is for Jsp config
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

### 2.3  整合测试

新建 controller 和 show.jsp 测试整合是否成功：

```java
@Slf4j
@Controller
@RequestMapping("index")
public class JspController {

    @RequestMapping
    public String jsp(Model model){
        UserDto userDto = new UserDto("Frank", 18, 1000.21f, LocalDate.now());
        model.addAttribute("userDto",userDto);
        log.info(userDto.toString());
        return "show";
    }

}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/show.css">
</head>
<body>
<ul>
    <li>姓名: ${userDto.name}</li>
    <li>年龄: ${userDto.age}</li>
</ul>
</body>
</html>
```

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-jsp/index.png"/> </div>
