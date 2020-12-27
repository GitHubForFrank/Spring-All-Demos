# Spring Boot 集成 Swagger2 打造在线接口文档

<nav>
<a href="#一概念综述">一、概念综述</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-SpringFox">1.1 SpringFox </a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-Swagger">1.2 Swagger</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#13-关联关系">1.3 关联关系</a><br/>
<a href="#二集成-Swagger-20">二、集成 Swagger 2.0</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-基本依赖">2.1 基本依赖</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-Swagger--配置">2.2 Swagger  配置 </a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-Swagger-注解">2.3 Swagger 注解 </a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-可视化接口文档">2.4 可视化接口文档</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#25-接口测试">2.5 接口测试</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#26-遗留问题部分">2.6 遗留问题部分</a><br/>
</nav>

## 一、概念综述

### 1.1 SpringFox 

SpringFox 是一个开源的 API 文档框架， 它的前身是 Swagger-SpringMVC ，能够完美的支持 SpringMVC 并将其中接口方法自动转换为接口文档。 目前 SpringFox 正致力于对更多 JSON API 规范和标准的扩展和支持，例如：[swagger](http://swagger.io/)，[RAML](http://raml.org/) 和 [jsonapi](http://jsonapi.org/)。

### 1.2 Swagger

Swagger 是一个规范框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务，支持 API 全生命周期的开发和管理。它是一个综合的开源项目，包含 [swagger-core](https://github.com/swagger-api/swagger-core)、[swagger-ui](https://github.com/swagger-api/swagger-ui)、[swagger-codegen](https://github.com/swagger-api/swagger-codegen)、[swagger-editor](https://github.com/swagger-api/swagger-editor) 等多个子项目：

+ **swagger-core**：Swagger Core 是 OpenAPI 规范（以前称为 Swagger 规范）的 Java 实现。

+ **swagger-ui**：根据可视化文档，提供与 API 资源的可视化交互。

+ **swagger-codegen**：开源的代码生成器，根据 Swagger 定义的 RESTful API 可以自动建立服务端和客户端的连接。

+ **swagger-editor**：开源的 API 文档编辑器。

下图为 swagger-ui 提供的文档可视化界面示例：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/Swagger_UI.png"/> </div>

### 1.3 关联关系

OpenAPI、Swagger、SpringFox 之间的关联关系可以表述为：**Swagger Core 是 Open API 规范的 Java 实现，而 SpringFox 则提供了 Swagger 与 Spring 的集成支持**。<br/>



## 二、集成 Swagger 2.0

### 2.1 基本依赖

```xml
<!--swagger2-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<!--swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

### 2.2 Swagger  配置 

进行 Swagger 个性化配置，并使用 @EnableSwagger2 开启 Swagger 支持。另外，虽然 Swagger 是一个非常易用的接口调试插件，但是有可能会导致接口信息泄露，所以建议在开发环境和测试环境开启，但在生产环境关闭。这里一共给出三种 Swagger 开启和关闭的切换方法：

1. 如下面代码所示，在配置文件中增加自定义的开关参数，并在创建 Docket 时，在链式调用的 enable() 方法中传入。
2. 在 SwaggerConfig 配置类上添加 `@Profile({"dev","test"}) ` 注解，指明仅在开发环境和测试环境下激活此配置，并在打包部署时使用 spring.profiles.active 指明具体的环境。
3. 在配置文件中配置自定义的开关参数，并在 SwaggerConfig 配置类上添加 `@ConditionalOnProperty(name = "swagger.enable", havingValue = "true") `，指明配置类的生效条件。@ConditionalOnProperty 注解能够控制某个配置类是否生效。具体操作是通过 name 和 havingValue 属性来实现，name 对应配置文件中的某个属性，如果该值为空，则返回 false；如果值不为空，则将该值与 havingValue 指定的值进行比较，如果一样则返回 true；否则返回 false。如果返回值为 false，则对应的配置不生效；为 true 则生效。

以下是第一种开关配置方式的使用示例(为了方便看log，配置了logback和tomcat)：

SwaggerConfig 类
```java
@Slf4j
@Configuration
@PropertySource(value = "classpath:swagger2.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean swaggerEnable;

    @Value("${swagger2.basePackage}")
    private String basePackage;
    @Value("${swagger2.title}")
    private String title;
    @Value("${swagger2.description}")
    private String description;
    @Value("${swagger2.termsOfServiceUrl}")
    private String termsOfServiceUrl;
    @Value("${swagger2.version}")
    private String version;
    @Value("${swagger2.contact.name}")
    private String contactName;
    @Value("${swagger2.contact.url}")
    private String contactUrl;
    @Value("${swagger2.contact.email}")
    private String contactEmail;

    @Autowired
    private TypeResolver typeResolver;
    @Autowired
    private SwaggerHttpHeader swaggerHttpHeader;

    /**
     * 开发和测试环境下可以开启swagger辅助进行调试,而生产环境下可以关闭或者进行相应的权限控制，防止接口信息泄露
     * @return
     */
    @Bean
    public Docket createDocket4AllApis(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .groupName("All APIs")
                .apiInfo(apiInfo(title))
                .select()
                .apis(getPredicate(basePackage))
                .paths(Predicates.not(PathSelectors.regex("/error.*|/actuator.*")))
                .build()
                .alternateTypeRules(getRule())
                .globalOperationParameters(this.getHeaders())
                .forCodeGeneration(true);
    }
    @Bean
    public String createDocketByController(){
        Map<String, Object> mapOfAllControl = SpringContextUtil.getApplicationContext().getBeansWithAnnotation(Api.class);
        if(mapOfAllControl!=null) {
            for (Map.Entry<String, Object> entry : mapOfAllControl.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                Class<?> declaringClass = value.getClass();

                String packageStr = declaringClass.getPackage().getName();
                ApiIgnore apiIgnore = declaringClass.getAnnotation(ApiIgnore.class);
                if (!packageStr.startsWith(basePackage) || apiIgnore != null) {
                    continue;
                }

                //sometime declaringClass.getName() contains $ , for example : com.zmz.testlog.api.controller.DictionaryController$$EnhancerBySpringCGLIB$$2f138dac
                String controllerName = declaringClass.getName().contains("$") ?
                        declaringClass.getName().substring(0, declaringClass.getName().indexOf('$')) : declaringClass.getName();

                Api api = declaringClass.getAnnotation(Api.class);
                String apiValue = api.value();

                Docket obj = new Docket(DocumentationType.SWAGGER_2)
                        .enable(swaggerEnable)
                        .groupName(apiValue)
                        .select()
                        .apis(getPredicate(controllerName))
                        .paths(PathSelectors.any())
                        .build()
                        .apiInfo(apiInfo(title))
                        .alternateTypeRules(getRule())
                        .globalOperationParameters(this.getHeaders())
                        .forCodeGeneration(true);

                registerBean(key, obj);
            }
        }
        return "createDocketByController";
    }
    @Bean
    public String createDocketByPackage(){
        Set<String> packageList = getPackageList();
        if (!CollectionUtils.isEmpty(packageList)){
            packageList.forEach(packages -> {
                Docket obj = new Docket(DocumentationType.SWAGGER_2)
                        .enable(swaggerEnable)
                        .groupName(packages)
                        .apiInfo(apiInfo(packages))
                        .select()
                        .apis(RequestHandlerSelectors.basePackage(packages))
                        .paths(PathSelectors.any())
                        .build()
                        .globalOperationParameters(this.getHeaders());
                registerBean(packages,obj);
            });
        }
        return "createDocketByPackage";
    }

    private ApiInfo apiInfo(String title) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .termsOfServiceUrl(termsOfServiceUrl)
                .contact(new Contact(contactName, contactUrl, contactEmail))
                .version(version)
                .build();
    }
    private Predicate<RequestHandler> getPredicate(String splitCharacter){
        return new Predicate<RequestHandler>() {
            @Override
            public boolean apply(RequestHandler input) {
                boolean tempInd = false;
                Class<?> declaringClass = input.declaringClass();
                if(declaringClass.isAnnotationPresent(Api.class)) {
                    String conName = declaringClass.getName().contains("$")?
                            declaringClass.getName().substring(0,declaringClass.getName().indexOf('$')):declaringClass.getName();
                    if(conName.startsWith(splitCharacter)){
                        tempInd = true;
                    }
                }
                return tempInd;
            }
        };
    }
    private AlternateTypeRule getRule() {
        return newRule(typeResolver.resolve(Response.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class));
    }
    private List<Parameter> getHeaders(){
        List<Parameter> parameters = new ArrayList<>();
        for(int iLoop=0; iLoop<swaggerHttpHeader.getName().size(); iLoop++){
            ParameterBuilder parameterBuilder = new ParameterBuilder();
            parameterBuilder
                    // Parameter type support : header, cookie, body, query etc
                    .parameterType("header")
                    .name(swaggerHttpHeader.getName().get(iLoop))
                    .defaultValue(swaggerHttpHeader.getDefaultValue().get(iLoop))
                    .description(swaggerHttpHeader.getDescription().get(iLoop))
                    .modelRef(new ModelRef(swaggerHttpHeader.getModelRef().get(iLoop)))
                    .required(swaggerHttpHeader.getRequired().get(iLoop)).build();
            parameters.add(parameterBuilder.build());
        }
        return parameters;
    }
    private Set<String> getPackageList(){

        String SPRING_FOX_PACKAGE = "springfox.documentation";
        String SPRING_PACKAGE = "org.springframework";
        String SWAGGER_BOOTSTRAP_UI_PACKAGE="com.github.xiaoymin.swaggerbootstrapui.web";
        String CORE_PACKAGE="com.zmz.core";
        String CORE_DEMO_PACKAGE="com.zmz.demo";

        Set<String> packageSet = new HashSet<>();
        Map<String, HandlerMapping> handlerMappingMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(SpringContextUtil.getApplicationContext() , HandlerMapping.class);
        for (HandlerMapping handlerMapping : handlerMappingMap.values()){
            if (handlerMapping instanceof RequestMappingHandlerMapping){
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()){
                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
                    String packageName = mappingInfoValue.getBeanType().getPackage().getName();
                    if (!packageName.contains(SPRING_FOX_PACKAGE) && !packageName.contains(SPRING_PACKAGE) && !packageName.contains(SWAGGER_BOOTSTRAP_UI_PACKAGE) && !packageName.contains(CORE_PACKAGE) && !packageName.contains(CORE_DEMO_PACKAGE)){
                        packageSet.add(packageName);
                    }
                }
            }
        }
        return packageSet;
    }
    private void registerBean(String key,Docket obj){
        log.info("registerBean - key: {}",key);
        ApplicationContext context =  SpringContextUtil.getApplicationContext();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)context.getAutowireCapableBeanFactory();
        String beanName = "SwaggerDocketBean_"+key;
        BeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClassName(obj.getClass().getName());
        beanDefinition.setScope("prototype");
        ((GenericBeanDefinition) beanDefinition).setSource(obj);
        ((GenericBeanDefinition) beanDefinition).setBeanClass(Docket.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);
        context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(obj, beanName);
        beanFactory.registerSingleton(beanName, obj);
    }

}

```

SwaggerHttpHeader 类，有的时候项目中需要定制公用的http header，参考如下配置：
```java
@Getter
@Setter
@Component
@PropertySource(value = "classpath:swagger2.properties", ignoreResourceNotFound = true, encoding = "UTF-8")
@ConfigurationProperties(prefix="swagger2.ui.header")
public class SwaggerHttpHeader {
    private List<String> name = new ArrayList<>();
    private List<String> defaultValue = new ArrayList<>();
    private List<String> description = new ArrayList<>();
    private List<String> modelRef = new ArrayList<>();
    private List<Boolean> required = new ArrayList<>();
}
```

SpringContextUtil 类
```java
@Component(value="springContextUtil")
public class SpringContextUtil implements ApplicationContextAware {

    private SpringContextUtil(){}

    private static ApplicationContext applicationContext = null;

    private synchronized static void setSyncApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setSyncApplicationContext(applicationContext);
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static Object getBean(Class clazz) {
        return applicationContext.getBean(clazz);
    }

}
```

Response 类,项目开发中定制化返回的Response
```java
@Setter
@Getter
@ApiModel(description = "Response data, will be null if HTTP.STATUS is not 1XX or 2XX")
public class Response<T> {
    @ApiModelProperty
    private Meta meta;

    @ApiModelProperty
    private T data;
}

```

Meta 类
```java
@Setter
@Getter
@ApiModel(value="ResponseMetaInfo")
public class Meta {
    @ApiModelProperty(value="completed time")
    private long completedTime;

    @ApiModelProperty(value="message list")
    private List<Message> messages;
}

```

Message 类
```java
@Setter
@Getter
@ToString
@ApiModel(value="ResponseMessage")
public class Message {

    @ApiModelProperty(value="message code")
    private String code;

    @ApiModelProperty(value="message description")
    private String description;

}
```

application.properties :
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
#Below is for Logback only
logging.config=classpath:logback-config.xml
logging.level.root=debug
logging.file.name=spring-boot-swagger2
logging.file.path=../logs/app

#-----------------------------------------------------------------------------------
#Below is for swagger
swagger.enable = true
```

swagger2.properties :
```properties
#-----------------------------------------------------------------------------------
#When Exist multiple basePackage, need to use "," to split them, like "com.zmz.example.ui.controller,com.zmz.example.api.controller"
swagger2.basePackage = com.zmz
swagger2.title = Demo API
swagger2.description = Demo Swagger API
swagger2.termsOfServiceUrl = http://api.zmz.com
swagger2.version = 1.0
swagger2.contact.name = XXX Support Team
swagger2.contact.url = https://www.baidu.com/
swagger2.contact.email = 123456789@qq.com

#-----------------------------------------------------------------------------------
#Below is for Configure Swagger UI API headers
swagger2.ui.header.name[0] = Accept-Language
swagger2.ui.header.defaultValue[0] = zh-CN
swagger2.ui.header.description[0] = Accept-Language
swagger2.ui.header.modelRef[0] = String
swagger2.ui.header.required[0] = false

swagger2.ui.header.name[1] = Authorization
swagger2.ui.header.defaultValue[1] = Bearer abcdefghijklm0123456789
swagger2.ui.header.description[1] = Authorization
swagger2.ui.header.modelRef[1] = String
swagger2.ui.header.required[1] = false

swagger2.ui.header.name[2] = tx-id
swagger2.ui.header.defaultValue[2] = abcde0123456
swagger2.ui.header.description[2] = Transaction Id
swagger2.ui.header.modelRef[2] = String
swagger2.ui.header.required[2] = false
```


### 2.3 Swagger 注解 

```java
@Slf4j
@Api(value = "产品API")
@RestController
public class ProductController {

    /***
     * 一个标准的 swagger 注解
     */
    @ApiOperation(notes = "查询所有产品", value = "产品查询接口")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "产品编号", paramType = "path", defaultValue = "1")
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "请求成功"),
            @ApiResponse(code = 400, message = "无效的请求"),
            @ApiResponse(code = 401, message = "未经过授权认证"),
            @ApiResponse(code = 403, message = "已经过授权认证，但是没有该资源对应的访问权限"),
            @ApiResponse(code = 404, message = "服务器找不到给定的资源,商品不存在"),
            @ApiResponse(code = 500, message = "服务器错误")
    })
    @GetMapping(value = "/product/{id}", produces = "application/json")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = new Product(id, "product" + id, new Date());
        return ResponseEntity.ok(product);
    }


    /***
     * 如果用实体类接收参数,则用实体类对应的属性名称指定参数
     */
    @ApiOperation(notes = "保存产品", value = "产品保存接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "产品编号", paramType = "body", defaultValue = "1"),
            @ApiImplicitParam(name = "name", value = "产品名称", paramType = "body"),
            @ApiImplicitParam(name = "date", value = "产品生产日期", paramType = "body")
    }
    )
    @PostMapping(value = "/product")
    public ResponseEntity<Void> saveProduct(@RequestBody Product product) {
        System.out.println(product);
        return ResponseEntity.ok().build();
    }


    /***
     * 在配置类中指明了该接口不被扫描到,可以在配置类中使用正则指定某一类符合规则的接口不被扫描到
     */
    @ApiOperation(notes = "该接口会被忽略", value = "产品保存接口")
    @PostMapping(value = "/ignore")
    public ResponseEntity<Product> ignore() {
        return ResponseEntity.ok().build();
    }

    /**
     * 不加上任何 swagger 相关的注解也会被扫描到 如果不希望被扫描到，需要用 @ApiIgnore 修饰
     */
    @PostMapping(value = "/normal")
    public ResponseEntity<Void> normal() {
        return ResponseEntity.ok().build();
    }

    @ApiIgnore
    @PostMapping(value = "/apiIgnore")
    public ResponseEntity<Void> apiIgnore() {
        return ResponseEntity.ok().build();
    }
}
```

Swagger 为了最大程度防止对逻辑代码的侵入，基本都是依靠注解来完成文档描述。常用注解如下：

| Annotation       | Attribute    | Target Property           | Description                                                  |
| ---------------- | ------------ | ------------------------- | ------------------------------------------------------------ |
| RequestHeader    | defaultValue | Parameter#defaultValue    | e.g. `@RequestHeader(defaultValue="${param1.defaultValue}")` |
| ApiModelProperty | value        | ModelProperty#description | e.g. `@ApiModelProperty(value="${property1.description}")`   |
| ApiModelProperty | description  | ModelProperty#description | e.g. `@ApiModelProperty(notes="${property1.description}")`   |
| ApiParam         | value        | Parameter#description     | e.g. `@ApiParam(value="${param1.description}")`              |
| ApiImplicitParam | value        | Parameter#description     | e.g. `@ApiImplicitParam(value="${param1.description}")`      |
| ApiOperation     | notes        | Operation#notes           | e.g. `@ApiOperation(notes="${operation1.description}")`      |
| ApiOperation     | summary      | Operation#summary         | e.g. `@ApiOperation(value="${operation1.summary}")`          |
| RequestParam     | defaultValue | Parameter#defaultValue    | e.g. `@RequestParam(defaultValue="${param1.defaultValue}")`  |

1. `@Api`：用在类上，说明该类的作用；

2. `@ApiOperation`：用在方法上，说明方法的作用；

3. `@ApiParam`：用在参数上，说明参数的作用；

4. `@ApiImplicitParams`：用在方法上说明方法参数的作用；

5. `@ApiImplicitParam`：用在 @ApiImplicitParams 注解中，描述每个具体参数；

6. `@ApiResponses`：一组 @ApiResponse 的配置；

7. `@ApiResponse`：请求返回的配置；

8. `@ResponseHeader`：响应头的配置；

9. `@ApiModel`：描述一个 Model 的信息（一般用在 post 创建的时候，使用@RequestBody 接收参数的场景）；

10. `@ApiModelProperty`：描述 model 的属性。

11. `@ApiIgnore`：可以用于类、方法、属性，代表该方法、类、属性不被 Swagger 的文档所管理。

    

### 2.4 可视化接口文档

接口文档访问地址：http://localhost:10190/app/swagger-ui.html ，文档主界面如下：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/swagger-ui-index.png"/> </div>

### 2.5 接口测试

Swagger-UI 除了提供接口可视化的功能外，还可以用于接口测试。点击对应接口的 `try it out` 按钮，然后输入对应的参数的值，最后点击下方的 `Execute` 按钮发送请求：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/swagger-try-it.png"/> </div>
<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/swagger-execute.png"/> </div>
<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/swagger-execute-response.png"/> </div>


### 2.6 遗留问题部分

1. api-docs 链接中的中文Bug

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/swagger-ui-bug01.png"/> </div>

2. 使用网站 http://editor.swagger.io/ 进行Yaml文件导出，发现Local失效

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-swagger2/swagger-ui-bug02.png"/> </div>






