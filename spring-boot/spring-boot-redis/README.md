# Spring Boot 整合 Redis


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-基本依赖">1.2 基本依赖</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#13-Redis数据库安装">1.3 Redis数据库安装</a><br/>
<a href="#二整合-Redis">二、整合 Redis</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-配置数据源">2.1 配置数据源</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22--基本操作">2.2  基本操作</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-自定义序列化器">2.3 自定义序列化器</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-单元测试">2.4 单元测试</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#25-数据库校验">2.5 数据库校验</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

- RedisConfig.java 实现了 redisTemplate 序列化与反序列化的配置；
- RedisOperation 和 RedisObjectOperation 分别封装了对基本类型和对象的操作。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-redis/project-structure.png"/> </div>

### 1.2 基本依赖

```xml
<!--redis starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<!--jackson 序列化包 -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.10.1</version>
</dependency>
```

### 1.3 Redis数据库安装
1. 百度网盘下载 Redis server和client

链接：https://pan.baidu.com/s/1WyXLEiHwltRlSOj-eWUiCQ 
提取码：wzf9

2. 开启Redis Server

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-redis/start-redis-server.png"/> </div>

3. 客户端查看，有如下两种方式

方式1：Redis自带的cmd命令台查看

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-redis/start-redis-client.png"/> </div>

方式2：RedisDesktopManager 客户端查看

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-redis/RedisDesktopManager-start.png"/> </div>



## 二、整合 Redis

### 2.1 配置数据源

```properties
#Below is for Redis only
spring.redis.host=127.0.0.1
spring.redis.port=6379
# 本地测试，不设置密码先
spring.redis.password=
# 默认采用的也是 0 号数据库 redis官方在4.0之后版本就不推荐采用单节点多数据库(db1-db15)的方式存储数据，如果有需要应该采用集群方式构建
spring.redis.database=0
# 如果是集群节点 采用如下配置指定节点
#spring.redis.cluster.nodes

```

### 2.2  基本操作

Spring Boot 提供了两个 Template 用于操作 Redis：

- **StringRedisTemplate**：由于 Redis 在大多数使用情况下都是操作字符串类型的存储，所以 Spring Boot 将对字符串的操作单独封装在 StringRedisTemplate 中 ；
- **RedisTemplate<Object, Object>** ：用于操作任意类型的 Template。

```java
@Component
public class RedisOperation {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /***
     * 操作普通字符串
     */
    public void StringSet(String key, String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /***
     * 操作列表
     */
    public void ListSet(String key, List<String> values) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        values.forEach(value -> listOperations.leftPush(key, value));
    }

    /***
     * 操作集合
     */
    public void SetSet(String key, Set<String> values) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        values.forEach(value -> setOperations.add(key, value));
    }

    /***
     * 获取字符串
     */
    public String StringGet(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /***
     * 列表弹出元素
     */
    public String ListLeftPop(String key) {
        ListOperations<String, String> listOperations = redisTemplate.opsForList();
        return listOperations.leftPop(key, 2, TimeUnit.SECONDS);
    }

    /***
     * 集合弹出元素
     */
    public String SetPop(String key) {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        return setOperations.pop(key);
    }

}
```

```java
@Component
public class RedisObjectOperation {

    @Autowired
    private RedisTemplate<Object, Object> objectRedisTemplate;

    /***
     * 操作对象
     */
    public void ObjectSet(Object key, Object value) {
        ValueOperations<Object, Object> valueOperations = objectRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /***
     * 操作元素为对象列表
     */
    public void ListSet(Object key, List<Object> values) {
        ListOperations<Object, Object> listOperations = objectRedisTemplate.opsForList();
        values.forEach(value -> listOperations.leftPush(key, value));
    }

    /***
     * 操作元素为对象集合
     */
    public void SetSet(Object key, Set<Object> values) {
        SetOperations<Object, Object> setOperations = objectRedisTemplate.opsForSet();
        values.forEach(value -> setOperations.add(key, value));
    }

    /***
     * 获取对象
     */
    public Object ObjectGet(Object key) {
        ValueOperations<Object, Object> valueOperations = objectRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    /***
     * 列表弹出元素
     */
    public Object ListLeftPop(Object key) {
        ListOperations<Object, Object> listOperations = objectRedisTemplate.opsForList();
        return listOperations.leftPop(key, 2, TimeUnit.SECONDS);
    }

    /***
     * 集合弹出元素
     */
    public Object SetPop(Object key) {
        SetOperations<Object, Object> setOperations = objectRedisTemplate.opsForSet();
        return setOperations.pop(key);
    }

}
```

### 2.3 自定义序列化器

Spring Boot 的 RedisTemplate 本身是实现了对象的序列化与反序列化的，但是这里的序列化默认采用的是 JDK 的序列化方式JdkSerializationRedisSerializer.serialize() 序列化为二进制码，此时如果我们在命令行中使用 get 命令去获取数据时候，得到的就是一串不直观的二进制码，所以我们尽量将其序列化为直观的 json 格式存储。自定义序列化器的方式如下：

```java
/**
 * 自定义序列化器
 * 不定义的话默认采用的是serializer.JdkSerializationRedisSerializer.serialize()序列化为二进制字节码 存储在数据库中不直观
 * @author ASNPHDG
 * @create 2020-01-27 12:29
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用 Jackson2JsonRedisSerialize  需要导入依赖 com.fasterxml.jackson.core jackson-databind
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        // 第一个参数表示: 表示所有访问者都受到影响 包括 字段, getter / isGetter,setter，creator
        // 第二个参数表示: 所有类型的访问修饰符都是可接受的，不论是公有还有私有表变量都会被序列化
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        // 设置 key,value 序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
```

### 2.4 单元测试

```java
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={SpringBootRedisApplication.class})
public class RedisObjectTests {

    @Autowired
    private RedisObjectOperation redisOperation;

    @Test
    public void StringOperation() {
        Programmer programmer = new Programmer("小明", 12, 3334.3f, new Date());
        redisOperation.ObjectSet("programmer", programmer);
        Programmer objectGet = (Programmer) redisOperation.ObjectGet("programmer");
        Assert.assertEquals(objectGet, programmer);
    }

    @Test
    public void ListOperation() {
        Programmer programmer01 = new Programmer("小明", 12, 3334.3f, new Date());
        Programmer programmer02 = new Programmer("小红", 12, 3334.3f, new Date());
        redisOperation.ListSet("programmerList", Arrays.asList(programmer01, programmer02));
        Programmer programmer = (Programmer) redisOperation.ListLeftPop("programmerList");
        Assert.assertEquals(programmer.getName(), "小红");
    }

    /*
     * 需要注意的是Redis的集合（set）不仅不允许有重复元素，并且集合中的元素是无序的，
     * 不能通过索引下标获取元素。哪怕你在java中传入的集合是有序的newLinkedHashSet，但是实际在Redis存储的还是无序的集合
     */
    @Test
    public void SetOperation() {
        Programmer programmer01 = new Programmer("小明", 12, 3334.3f, new Date());
        Programmer programmer02 = new Programmer("小爱", 12, 3334.3f, new Date());
        Programmer programmer03 = new Programmer("小红", 12, 3334.3f, new Date());
        redisOperation.SetSet("programmerSet", Sets.newLinkedHashSet(programmer01, programmer02, programmer03));
        Programmer programmer = (Programmer) redisOperation.SetPop("programmerSet");
        Assert.assertNotNull(programmer);
    }

}
```

### 2.5 数据库校验

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-redis/RedisDesktopManager-query.png"/> </div>



