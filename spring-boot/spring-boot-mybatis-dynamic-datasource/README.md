# Spring Boot 整合 Mybatis 实现动态数据源


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-主要依赖">1.2 主要依赖</a><br/>
<a href="#二整合-Mybatis 实现动态数据源">二、整合 Mybatis 实现动态数据源</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-配置applicationproperties">2.1 配置application.properties</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-核心配置文件">2.2 核心配置文件</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-单元测试">2.3 单元测试</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-其他参考">2.4 其他参考</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

- 项目涉及表的建表语句放置在 https://github.com/GitHubForFrank/Spring-All-Demos/tree/master/00-materials/database-scripts 下；

- 本项目中创建了三个不同的repository分别对应三个不同的SqlServer数据源，是本人在百度云服务器创建的数据库，已经做了内网穿透，公网即可访问。至于百度云服务器到期日待定。三个repository共用一个Mapper文件（即三个数据库中创建了相同结构的表）：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-mybatis-dynamic-datasource/project-structure.png"/> </div>

### 1.2 主要依赖

按照 Spring 官方对于自定义的 starter 命名规范的要求：

- 官方的 starter 命名：spring-boot-starter-XXXX
- 其他第三方 starter 命名：XXXX-spring-boot-starte

所以 Mybatis 的 starter 命名为 mybatis-spring-boot-starter，如果有自定义 starter 需求，也需要按照此命名规则进行命名。

```xml
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
<!-- Spring Boot 依赖-->
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-aop</artifactId>
</dependency>
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
<!-- Mybatis 依赖 -->
<dependency>
	<groupId>org.mybatis.spring.boot</groupId>
	<artifactId>mybatis-spring-boot-starter</artifactId>
	<version>1.3.2</version>
</dependency>
<!-- 数据库 依赖 -->
<dependency>
	<groupId>com.microsoft.sqlserver</groupId>
	<artifactId>mssql-jdbc</artifactId>
	<version>7.2.2.jre8</version>
</dependency>
<!-- 其他依赖 -->
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
</dependency>
```

spring boot 与 mybatis 版本的对应关系：

| MyBatis-Spring-Boot-Starter | [MyBatis-Spring](http://www.mybatis.org/spring/index.html#Requirements) | Spring Boot   |
| --------------------------- | ------------------------------------------------------------ | ------------- |
| **1.3.x (1.3.1)**           | 1.3 or higher                                                | 1.5 or higher |
| **1.2.x (1.2.1)**           | 1.3 or higher                                                | 1.4 or higher |
| **1.1.x (1.1.1)**           | 1.3 or higher                                                | 1.3 or higher |
| **1.0.x (1.0.2)**           | 1.2 or higher                                                | 1.3 or higher |

## 二、整合 Mybatis 实现动态数据源

### 2.1 配置application.properties

Spring Boot 2.x 版本默认采用 Hikari 作为数据库连接池，Hikari 是目前 Java 平台性能最好的连接池，性能好于 druid。

如下是 tomcat server 、 logback 、 DataSource、 mybatis 的相关配置：

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
logging.file.name=spring-boot-mybatis-dynamic-datasource
logging.file.path=../logs/app

#-----------------------------------------------------------------------------------
#Below is for Primary DB Configuration
spring.datasource.master.url=jdbc:sqlserver://182.61.149.71:1433;DatabaseName=pds_demo
spring.datasource.master.driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.master.username=kong
spring.datasource.master.password=2020Jan11
spring.datasource.master.type=com.zaxxer.hikari.HikariDataSource

spring.datasource.cluster[0].key=slave1
spring.datasource.cluster[0].url=jdbc:sqlserver://182.61.149.71:1433;DatabaseName=pds_demo01
spring.datasource.cluster[0].driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.cluster[0].username=kong
spring.datasource.cluster[0].password=2020Jan11
spring.datasource.cluster[0].idle-timeout=20000
spring.datasource.cluster[0].type=com.zaxxer.hikari.HikariDataSource

spring.datasource.cluster[1].key=slave2
spring.datasource.cluster[1].url=jdbc:sqlserver://182.61.149.71:1433;DatabaseName=pds_demo02
spring.datasource.cluster[1].driver-class-name=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.cluster[1].username=kong
spring.datasource.cluster[1].password=2020Jan11

#-----------------------------------------------------------------------------------
#Below is for Mybatis Configuration
mybatis.mapperLocations = classpath:mybatis/mapper/*.xml
mybatis.typeAliasesPackage = com.zmz.app.infrastructure.dao.entity
mybatis.configLocation = classpath:/mybatis/config/mybatis-config.xml

```

### 2.2 核心配置文件

注解类： 

```java
/**
 * 切换数据注解 可以用于类或者方法级别 方法级别优先级 > 类级别
 * @author ASNPHDG
 * @create 2020-01-13 11:36 AM
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    /**
     * 该值即key值
     * @return
     */
    String value() default "master";
}
```

AOP类： 

```java
@Slf4j
@Aspect
@Component
@Order(-1900)
public class DataSourceAspect {

    protected static final ThreadLocal<String> preDatasourceHolder = new ThreadLocal<>();

    @Pointcut(value = "@within(com.zmz.core.config.datasource.annotation.DataSource) || @annotation(com.zmz.core.config.datasource.annotation.DataSource)")
    protected void datasourceAspect() {
    }

    @Before("datasourceAspect()")
    public void changeDataSource(JoinPoint point) {
        String key = determineDatasource(point);
        if (DataSourceContextHolder.dataSourceIds.contains(key)) {
            preDatasourceHolder.set(DataSourceContextHolder.getDataSourceRouterKey());
            DataSourceContextHolder.setDataSourceRouterKey(key);
        } else {
            log.info("数据源[{}]不存在，使用默认数据源 >{}", key, point.getSignature());
        }
    }

    @After("datasourceAspect()")
    public void restoreDataSourceAfterMethodExecution() {
        DataSourceContextHolder.setDataSourceRouterKey(preDatasourceHolder.get());
        preDatasourceHolder.remove();
    }

    private String determineDatasource(JoinPoint jp) {
        String methodName = jp.getSignature().getName();
        Class targetClass = jp.getSignature().getDeclaringType();
        String dataSourceForTargetClass = resolveDataSourceFromClass(targetClass);
        String dataSourceForTargetMethod = resolveDataSourceFromMethod(targetClass, methodName);
        String resultDS = determinateDataSource(dataSourceForTargetClass, dataSourceForTargetMethod);
        log.info("AOP 切换 Key 所在类 ：{}",targetClass.getName());
        log.info("AOP 切换 Key 为：{}",resultDS);
        return resultDS;
    }
    private String resolveDataSourceFromMethod(Class targetClass, String methodName) {
        Method m = findUniqueMethod(targetClass, methodName);
        if (m != null) {
            DataSource choDs = m.getAnnotation(DataSource.class);
            return resolveDataSourceName(choDs);
        }
        return null;
    }
    private String determinateDataSource(String classDS, String methodDS) {
        return methodDS == null ? classDS : methodDS;
    }
    private String resolveDataSourceFromClass(Class targetClass) {
        DataSource classAnnotation = (DataSource) targetClass.getAnnotation(DataSource.class);
        return null != classAnnotation ? resolveDataSourceName(classAnnotation) : null;
    }
    private String resolveDataSourceName(DataSource ds) {
        return ds == null ? null : ds.value();
    }
    private static Method findUniqueMethod(Class<?> clazz, String name) {
        Class<?> searchType = clazz;
        while (searchType != null) {
            Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
            for (Method method : methods) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
            searchType = searchType.getSuperclass();
        }
        return null;
    }

}
```

DataSource 上下文类： 

```java
@Slf4j
public class DataSourceContextHolder {

    /**
     * 存储已经注册的数据源的key
     */
    public static List<String> dataSourceIds = new ArrayList<>();

    /**
     * 线程级别的私有变量
     */
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<>();

    public static String getDataSourceRouterKey () {
        return HOLDER.get();
    }

    public static void setDataSourceRouterKey (String dataSourceRouterKey) {
        HOLDER.set(dataSourceRouterKey);
    }

    /**
     * 设置数据源之前一定要先移除
     */
    public static void removeDataSourceRouterKey () {
        HOLDER.remove();
    }

    /**
     * 判断指定DataSrouce当前是否存在
     *
     * @param dataSourceId
     * @return
     */
    public static boolean containsDataSource(String dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }

}
```

动态数据源注册类：

```java
/**
 * 动态数据源注册
 * 实现 ImportBeanDefinitionRegistrar 实现数据源注册
 * 实现 EnvironmentAware 用于读取application.properties 配置
 * @author ASNPHDG
 * @create 2020-01-13 11:38 AM
 */
@Slf4j
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    /**
     * 配置上下文（也可以理解为配置文件的获取工具）
     */
    private Environment evn;

    /**
     * 别名
     */
    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    /**
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    /**
     * 存储我们注册的数据源
     */
    private Map<String, DataSource> customDataSources = new HashMap<>();

    /**
     * 参数绑定工具 springboot2.0新推出
     */
    private Binder binder;

    /**
     * ImportBeanDefinitionRegistrar接口的实现方法，通过该方法可以按照自己的方式注册bean
     *
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 获取所有数据源配置
        Map config, defaultDataSourceProperties;
        defaultDataSourceProperties = binder.bind("spring.datasource.master", Map.class).get();
        // 获取数据源类型
        String typeStr = evn.getProperty("spring.datasource.master.type");
        // 获取数据源类型
        Class<? extends DataSource> clazz = getDataSourceType(typeStr);
        // 绑定默认数据源参数 也就是主数据源
        DataSource consumerDatasource, defaultDatasource = bind(clazz, defaultDataSourceProperties);
        DataSourceContextHolder.dataSourceIds.add("master");
        log.info("注册默认数据源成功");
        // 获取其他数据源配置
        List<Map> configs = binder.bind("spring.datasource.cluster", Bindable.listOf(Map.class)).get();
        // 遍历从数据源
        for (int i = 0; i < configs.size(); i++) {
            config = configs.get(i);
            clazz = getDataSourceType((String) config.get("type"));
            defaultDataSourceProperties = config;
            // 绑定参数
            consumerDatasource = bind(clazz, defaultDataSourceProperties);
            // 获取数据源的key，以便通过该key可以定位到数据源
            String key = config.get("key").toString();
            customDataSources.put(key, consumerDatasource);
            // 数据源上下文，用于管理数据源与记录已经注册的数据源key
            DataSourceContextHolder.dataSourceIds.add(key);
            log.info("注册数据源 {} 成功", key);
        }
        // bean定义类
        GenericBeanDefinition define = new GenericBeanDefinition();
        // 设置bean的类型，此处DynamicRoutingDataSource是继承AbstractRoutingDataSource的实现类
        define.setBeanClass(DynamicRoutingDataSource.class);
        // 需要注入的参数
        MutablePropertyValues mpv = define.getPropertyValues();
        // 添加默认数据源，避免key不存在的情况没有数据源可用
        mpv.add("defaultTargetDataSource", defaultDatasource);
        // 添加其他数据源
        mpv.add("targetDataSources", customDataSources);
        // 将该bean注册为datasource，不使用springboot自动生成的datasource
        beanDefinitionRegistry.registerBeanDefinition("datasource", define);
        log.info("注册数据源成功，一共注册{}个数据源", customDataSources.keySet().size() + 1);
    }

    /**
     * 通过字符串获取数据源class对象
     *
     * @param typeStr
     * @return
     */
    private Class<? extends DataSource> getDataSourceType(String typeStr) {
        Class<? extends DataSource> type;
        try {
            if (StringUtils.hasLength(typeStr)) {
                // 字符串不为空则通过反射获取class对象
                type = (Class<? extends DataSource>) Class.forName(typeStr);
            } else {
                // 默认为hikariCP数据源，与springboot默认数据源保持一致
                type = HikariDataSource.class;
            }
            return type;
        } catch (Exception e) {
            //无法通过反射获取class对象的情况则抛出异常，该情况一般是写错了，所以此次抛出一个runtimeexception
            throw new IllegalArgumentException("can not resolve class with type: " + typeStr);
        }
    }

    /**
     * 绑定参数，以下三个方法都是参考DataSourceBuilder的bind方法实现的，目的是尽量保证我们自己添加的数据源构造过程与springboot保持一致
     *
     * @param result
     * @param properties
     */
    private void bind(DataSource result, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 将参数绑定到对象
        binder.bind(ConfigurationPropertyName.EMPTY, Bindable.ofInstance(result));
    }

    private <T extends DataSource> T bind(Class<T> clazz, Map properties) {
        ConfigurationPropertySource source = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(new ConfigurationPropertySource[]{source.withAliases(aliases)});
        // 通过类型绑定参数并获得实例对象
        return binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(clazz)).get();
    }

    /**
     * @param clazz
     * @param sourcePath 参数路径，对应配置文件中的值，如: spring.datasource
     * @param <T>
     * @return
     */
    private <T extends DataSource> T bind(Class<T> clazz, String sourcePath) {
        Map properties = binder.bind(sourcePath, Map.class).get();
        return bind(clazz, properties);
    }

    /**
     * EnvironmentAware接口的实现方法，通过aware的方式注入，此处是environment对象
     *
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        log.info("开始注册数据源");
        this.evn = environment;
        // 绑定配置器
        binder = Binder.get(evn);
    }
}
```

Entity和Vo转换的接口类：

一个entity一般对应数据库的一个表，在转成vo类的时候，某些字段可能会发生变化，例如数据库的男女可能是数字类型，vo对应String类型。

```java
public interface Translator<T, V> {
    /**
     * Translate entity bean to value object
     * */
    V E2VO(T entity, V vo);
    /**
     * Translate value object to entity
     * */
    T VO2E(T entity, V vo);

    default List<V> E2VOs(List<T> entities, List<V> vos){
        if (entities == null || entities.size() == 0) {
            return null;
        }
        List<V> result = new ArrayList<>();
        for (int iLoop = 0; iLoop < entities.size(); iLoop++) {
            V vo = null;
            if (vos != null && vos.size() >= iLoop) {
                vo = vos.get(iLoop);
            }
            T entity = entities.get(iLoop);
            result.add(E2VO(entity, vo));
        }
        return result;
    }

    default List<T> VO2Es(List<T> entities, List<V> vos){
        if(vos == null || vos.size()==0){
            return null;
        }
        List<T> result = new ArrayList<>();
        for(int iLoop=0; iLoop<vos.size(); iLoop++){
            T entity = null;
            if(entities != null && entities.size()>=iLoop){
                entity = entities.get(iLoop);
            }
            V vo = vos.get(iLoop);
            result.add(VO2E(entity, vo));
        }
        return result;
    }
}
```


### 2.3 单元测试

新建 UserMapper.java 和 UserMapper.xml，及其测试类：

```java
@Mapper
public interface UserMapper {
    UserEntity selectByPrimaryKey(Long id);
    List<UserEntity> queryAllUser();
    void insert(UserEntity user);
    void deleteById(Long id);
    void updateByPrimaryKeySelective(UserEntity user);
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.zmz.app.infrastructure.dao.mapper.UserMapper">

    <resultMap id="BaseResultMap" type="com.zmz.app.infrastructure.dao.entity.UserEntity">
        <id column="id" jdbcType="BIGINT" property="id" />
        <result column="name" jdbcType="VARCHAR" property="name" />
        <result column="dept" jdbcType="VARCHAR" property="dept" />
        <result column="phone" jdbcType="VARCHAR" property="phone" />
        <result column="website" jdbcType="VARCHAR" property="website" />
    </resultMap>

    <sql id="Base_Column_List">
        id, name, dept, phone, website
    </sql>

    <insert id="insert" parameterType="com.zmz.app.infrastructure.dao.entity.UserEntity">
        insert into tbl_user ( name, dept,
        phone, website)
        values (#{name,jdbcType=VARCHAR}, #{dept,jdbcType=VARCHAR},
        #{phone,jdbcType=VARCHAR}, #{website,jdbcType=VARCHAR})
    </insert>

    <delete id="deleteById" parameterType="java.lang.Long">
        delete from tbl_user where id = #{id,jdbcType=BIGINT}
    </delete>

    <update id="updateByPrimaryKeySelective" parameterType="com.zmz.app.infrastructure.dao.entity.UserEntity">
        update tbl_user
        <set>
            <if test="name != null">
                name = #{name,jdbcType=VARCHAR},
            </if>
            <if test="dept != null">
                dept = #{dept,jdbcType=VARCHAR},
            </if>
            <if test="phone != null">
                phone = #{phone,jdbcType=VARCHAR},
            </if>
            <if test="website != null">
                website = #{website,jdbcType=VARCHAR},
            </if>
        </set>
        where id = #{id,jdbcType=BIGINT}
    </update>

    <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from tbl_user
        where id = #{id,jdbcType=BIGINT}
    </select>

    <select id="queryAllUser" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from tbl_user
    </select>

</mapper>
```

新建 Repository 类，如下是使用数据源slave1的UserRepository01的实现类UserRepository01Impl：

```java
@Repository
@DataSource("slave1")
public class UserRepository01Impl implements UserRepository01 {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserTranslator userTranslator;

    @Override
    public UserModel queryById(long id) {
        UserEntity userEntity = userMapper.selectByPrimaryKey(id);
        return userTranslator.E2VO(userEntity,null);
    }

    @Override
    public List<UserModel> queryAllUser() {
        List<UserEntity> entityList = userMapper.queryAllUser();
        return userTranslator.E2VOs(entityList,null);
    }

    @Override
    public void create(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        userMapper.insert(userEntity);
    }

    @Override
    public void delete(long id) {
        userMapper.deleteById(id);
    }

    @Override
    public void updateUser(UserModel userModel) {
        UserEntity userEntity = userTranslator.VO2E(null,userModel);
        userMapper.updateByPrimaryKeySelective(userEntity);
    }

}

```

测试类：

```java
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DynamicDataSourceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository01 userRepository01;
    @Autowired
    private UserRepository02 userRepository02;

    @Test
    public void test_query(){
        System.out.println("=======================================");
        System.out.println("主数据源-测试查询功能 ......");
        List<UserModel> list = userRepository.queryAllUser();
        if(list!=null){
            for (UserModel userModel : list) {
                System.out.println(userModel);
            }
        }else{
            System.out.println("主数据源 没有查询到数据。");
        }

        System.out.println("=======================================");
        System.out.println("从数据源01-测试查询功能 ......");
        List<UserModel> list01 = userRepository01.queryAllUser();
        if(list01!=null){
            for (UserModel userModel : list01) {
                System.out.println(userModel);
            }
        }else{
            System.out.println("从数据源01 没有查询到数据。");
        }

        System.out.println("=======================================");
        System.out.println("从数据源02-测试查询功能 ......");
        List<UserModel> list02 = userRepository02.queryAllUser();
        if(list02!=null){
            for (UserModel userModel : list02) {
                System.out.println(userModel);
            }
        }else{
            System.out.println("从数据源02 没有查询到数据。");
        }
    }

    @Test
    public void test_create(){
        System.out.println("=======================================");
        System.out.println("主数据源-测试创建功能 ......");
        UserModel userModel = new UserModel("master-xiaoming","IT","123456","abc");
        userRepository.create(userModel);

        System.out.println("=======================================");
        System.out.println("从数据源01-测试创建功能 ......");
        UserModel userModel01 = new UserModel("slave1-xiaoming","IT","123456","abc");
        userRepository01.create(userModel01);

        System.out.println("=======================================");
        System.out.println("从数据源02-测试创建功能 ......");
        UserModel userModel02 = new UserModel("slave2-xiaoming","IT","123456","abc");
        userRepository02.create(userModel02);
    }

}
```


### 2.4 其他参考

mybatis 配置更多说明可以参考settings  http://www.mybatis.org/mybatis-3/zh/configuration.html

spring boot 2.0 默认采用Hikari 作为连接池 Hikari github地址 https://github.com/brettwooldridge/HikariCP

