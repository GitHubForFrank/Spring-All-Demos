# Spring Boot 整合 Mybatis 实现多数据源


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-主要依赖">1.2 主要依赖</a><br/>
<a href="#二整合-Mybatis 实现动态数据源">二、整合 Mybatis 实现动态数据源</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-配置applicationproperties和mybatisproperties">2.1 配置application.properties和mybatis.properties</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-核心配置文件">2.2 核心配置文件</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-单元测试">2.3 单元测试</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-其他参考">2.4 其他参考</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

- 项目涉及表的建表语句放置在 https://github.com/GitHubForFrank/Spring-All-Demos/tree/master/00-materials/database-scripts 下；

- 本项目中创建了三个不同的repository分别对应三个不同的SqlServer数据源，是本人在百度云服务器创建的数据库，已经做了内网穿透，公网即可访问。至于百度云服务器到期日待定。三个repository共用一个Mapper文件（即三个数据库中创建了相同结构的表）：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-mybatis-druid-multiple-datasource/project-structure.png"/> </div>

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

### 2.1 配置application.properties和mybatis.properties

Spring Boot 2.x 版本默认采用 Hikari 作为数据库连接池，Hikari 是目前 Java 平台性能最好的连接池，性能好于 druid。

如下是 tomcat server 、 logback 、 DataSource、 mybatis 的相关配置：

application.properties

```properties
#-----------------------------------------------------------------------------------
#Below is for Tomcat Server Configuration
server.port=10190
server.servlet.context-path=/app
server.tomcat.max-threads=800
server.tomcat.uri-encoding=UTF-8
server.tomcat.basedir=../logs/tomcat
server.tomcat.accesslog.enabled=true
server.tomcat.accesslog.pattern=%t %a "%r" %s (%D ms)
server.tomcat.accesslog.directory=access

#-----------------------------------------------------------------------------------
#Below is for Logback Configuration
logging.config=classpath:logback-config.xml
logging.level.root=info
logging.file.name=spring-boot-mybatis-druid-multiple-datasource
logging.file.path=../logs/app

#-----------------------------------------------------------------------------------
#Below is for DB Configuration
spring.datasource.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.url=jdbc:sqlserver://182.61.149.71:1433;DatabaseName=pds_demo
spring.datasource.username=kong
spring.datasource.password=2020Jan11

spring.datasource.druid.slave.username=kong
spring.datasource.druid.slave.password=2020Jan11
spring.datasource.druid.slave.driverClassName=com.microsoft.sqlserver.jdbc.SQLServerDriver
spring.datasource.druid.slave.url=jdbc:sqlserver://182.61.149.71:1433;DatabaseName=pds_demo01

```

mybatis.properties

```properties
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
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    DataSourceEnum value() default DataSourceEnum.MASTER;
}
```

DataSource枚举：

```java
public enum DataSourceEnum {

    MASTER("master"),SLAVE("slave");

    private String value;

    DataSourceEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
```

AOP类： 

```java
@Slf4j
@Component
@Aspect
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
        return ds == null ? null : ds.value().getValue();
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

从数据库的Condition类：

```java
@Component
public class MultipleDataSourceCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment env =context.getEnvironment();
        String slaveDataSourceUrl = "spring.datasource.druid.slave.url";
        if(env.getProperty(slaveDataSourceUrl) != null){
            return true;
        }
        return false;
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

Druid 配置类：

```java
@Configuration
public class DruidConfig {

    @Autowired
    private Environment env;

    @Bean("master")
    @Primary
    public DataSource masterDataSource() throws Exception{
        Properties properties = getDataSourceProperties("spring.datasource");
        return DruidDataSourceFactory.createDataSource(properties);
    }

    @Bean("slave")
    @Conditional(MultipleDataSourceCondition.class)
    public DataSource slaveDataSource() throws Exception {
        Properties properties = getDataSourceProperties("spring.datasource.druid.slave");
        return DruidDataSourceFactory.createDataSource(properties);
    }

    @Bean
    public ServletRegistrationBean servletRegistration() {
        // 添加初始化参数
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new StatViewServlet());
        servletRegistration.addUrlMappings("/druid/*");
        // IP 白名单
        servletRegistration.addInitParameter("allow", "127.0.0.1");
        // IP黑名单（当白名单和黑名单中都存在时，拒绝优先于允许。）：如果满足拒绝，则提示：对不起，您不允许查看此页面。
        servletRegistration.addInitParameter("deny", "192.168.1.73");
        // 登录可以查看信息的帐户密码。
        servletRegistration.addInitParameter("loginUsername", "admin");
        servletRegistration.addInitParameter("loginPassword", "123456");
        // 定义是否可以重置数据
        servletRegistration.addInitParameter("resetEnable", "false");
        return servletRegistration;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息。
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    private Properties getDataSourceProperties(String prefix) {
        Properties properties = new Properties();
        setProperties(properties,"initialSize",prefix+".minIdle");
        setProperties(properties,"minIdle",prefix+".initialSize");
        setProperties(properties,"maxActive",prefix+".maxActive");
        setProperties(properties,"maxWait",prefix+".maxWait");
        setProperties(properties,"timeBetweenEvictionRunsMillis",prefix+".timeBetweenEvictionRunsMillis");
        setProperties(properties,"minEvictableIdleTimeMillis",prefix+".minEvictableIdleTimeMillis");
        setProperties(properties,"validationQuery",prefix+".validationQuery");
        setProperties(properties,"filters",prefix+".druid.sys.filters");
        setProperties(properties,"validationQueryTimeout",prefix+".validationQueryTimeout");
        setProperties(properties,"testWhileIdle",prefix+".testWhileIdle");
        setProperties(properties,"testOnBorrow",prefix+".testOnBorrow");
        setProperties(properties,"testOnReturn",prefix+".testOnReturn");

        //如下不做非空判断,是连接数据库必须字段
        properties.put("driverClassName", env.getProperty(prefix+".driverClassName"));
        properties.put("url", env.getProperty(prefix+".url"));
        properties.put("username", env.getProperty(prefix+".username"));
        properties.put("password", env.getProperty(prefix+".password"));
        System.out.println("=============================");
        System.out.println(properties);
        System.out.println("=============================");
        return properties;
    }

    private void setProperties(Properties properties,String key,String envKey){
        String value = env.getProperty(envKey);
        if(value!=null){
            properties.put(key, value);
        }
    }

}

```

Mybatis 配置类：

```java
@Slf4j
@Configuration
@PropertySources({ @PropertySource(value = "classpath:mybatis.properties", ignoreResourceNotFound = true, encoding = "UTF-8") })
@AutoConfigureAfter({DruidConfig.class})
@EnableTransactionManagement
public class MybatisConfig {

    @Value("${mybatis.typeAliasesPackage}")
    private String typeAliasesPackage;

    @Value("#{'${mybatis.mapperLocations}'.split(',')}")
    private List<String> mapperLocations;

    @Value("${mybatis.configLocation}")
    private String configLocation;

    @Autowired
    @Qualifier(value = "master")
    DataSource masterDataSource;

    @Autowired(required = false)
    @Qualifier(value = "slave")
    DataSource slaveDataSource;

    @Bean(name = "multipleDataSource")
    public DataSource multipleDataSource() {
        MultipleDataSource multipleDataSource = new MultipleDataSource();
        Map< Object, Object > targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceEnum.MASTER.getValue(), masterDataSource);
        // 数据源上下文，用于管理数据源与记录已经注册的数据源key
        DataSourceContextHolder.dataSourceIds.add(DataSourceEnum.MASTER.getValue());
        if(slaveDataSource!=null){
            targetDataSources.put(DataSourceEnum.SLAVE.getValue(), slaveDataSource);
            DataSourceContextHolder.dataSourceIds.add(DataSourceEnum.SLAVE.getValue());
        }
        //添加数据源
        multipleDataSource.setTargetDataSources(targetDataSources);
        //设置默认数据源
        multipleDataSource.setDefaultTargetDataSource(masterDataSource);
        return multipleDataSource;
    }

    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(multipleDataSource());

        // 阅读类型别名包装
        sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);

        // 设置xxxxMapper.xml和Java XxxxMapper.java文件位置的所有映射器位置
        List<Resource> resourcesList = getAllMappersLocation();
        if ( null != resourcesList ) {
            sessionFactoryBean.setMapperLocations( resourcesList.toArray(new Resource[0]) );
        }

        // 设置mybatis-config.xml配置文件位置
        log.info("configLocation = " + configLocation);
        sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));

        // 分页和SQL打印插件
        Interceptor[] plugins = new Interceptor[]{paginationInterceptor(), sqlPrintInterceptor()};
        sessionFactoryBean.setPlugins(plugins);

        // 全局配置
        DbConfig dbConfig = new DbConfig();
//        dbConfig.setIdType(IdType.AUTO);
//        dbConfig.setColumnLike(true);
//        dbConfig.setTablePrefix("t_");
//        dbConfig.setCapitalMode(false);
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setDbConfig(dbConfig);
        globalConfig.setRefresh(true);
        // 操作数据库（insert/update）的时候自动填充插件
        globalConfig.setMetaObjectHandler(new MetaObjectHandlerConfig());
        sessionFactoryBean.setGlobalConfig(globalConfig);
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PlatformTransactionManager multipleTransactionManager(@Qualifier("multipleDataSource")DataSource multipleDataSource) {
        return new DataSourceTransactionManager(multipleDataSource);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public SqlPrintInterceptor sqlPrintInterceptor(){
        return new SqlPrintInterceptor();
    }

    public List<Resource> getAllMappersLocation(){
        log.info("mapperLocations: " + (null == mapperLocations ? "null" : ""+mapperLocations.size()));
        List<Resource> resourcesList = null;
        for ( String mapperLocation : mapperLocations) {
            log.info("mapperLocation:" + mapperLocation);
            try {
                Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocation);
                if (null != resources && resources.length > 0) {
                    log.info("resources:" + resources.length);
                    if (null == resourcesList) {
                        resourcesList = new ArrayList<>();
                    }
                    resourcesList.addAll(Arrays.asList(resources));
                }
            } catch (Exception ex) {
                //Ignore wrong mapperLocation and no-resource location
                log.debug("WRONG mapperLocation:" + mapperLocation);
            }
        }
        return resourcesList;
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
public class MultipleDataSourceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository01 userRepository01;

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

    }

}

```


### 2.4 其他参考

mybatis 配置更多说明可以参考settings  http://www.mybatis.org/mybatis-3/zh/configuration.html

spring boot 2.0 默认采用Hikari 作为连接池 Hikari github地址 https://github.com/brettwooldridge/HikariCP

