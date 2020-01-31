# Spring Boot Data JPA


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-基本依赖">1.2 基本依赖</a><br/>
<a href="#二-使用-Data-JPA">二、 使用 Data JPA</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-数据源配置">2.1 数据源配置</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22-查询接口">2.2 查询接口</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23--单元测试">2.3  单元测试</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-data-jpa/project-structure.png"/> </div>

### 1.2 基本依赖

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
		<artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>

	<!--引入mysql驱动-->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>8.0.13</version>
	</dependency>

	<!-- Logback 依赖-->
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
		<optional>true</optional>
	</dependency>
</dependencies>
```

## 二、 使用 Data JPA

### 2.1 数据源配置

在 application.yml 中配置数据源：

```properties
#-----------------------------------------------------------------------------------
#Below is for Logback config
logging.config=classpath:logback-config.xml
logging.level.root=debug
logging.file.name=spring-boot-data-jpa
logging.file.path=../logs/app

#-----------------------------------------------------------------------------------
#Below is for DB config
spring.datasource.url=jdbc:mysql://182.61.149.71:3306/pds_demo01?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false
spring.datasource.username=kong
spring.datasource.password=2020Jan11
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
#Hibernate默认创建的表是myisam引擎，可以用以下方式指定为使用innodb创建表
spring.jpa.database-platform=org.hibernate.dialect.MySQL57Dialect
spring.jpa.show-sql=true

```

### 2.2 查询接口

```java
public interface ProgrammerRepository extends CrudRepository<Programmer, Integer> {

    /**
     * 方法名遵循命名规范的查询 更多命名规范可以参考官方文档所列出的这张表格
     * https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/#jpa.query-methods.query-creation
     * @param name
     * @return
     */
    List<Programmer> findAllByName(String name);

    /**
     *分页排序查询
     * @param pageable
     * @return
     */
    Page<Programmer> findAll(Pageable pageable);

    /**
     * 占位符查询
     *
     * @param name
     * @param salary
     * @param order
     * @return
     */
    @Query(value = "select u from Programmer u where u.name = ?1 or u.salary =  ?2")
    List<Programmer> findByConditionAndOrder(String name, float salary, Sort.Order order);


    /**
     * 传入参数名称
     *
     * @param name
     * @param age
     * @return
     */
    @Query("select u from Programmer u where u.name = :name or u.age = :age")
    Programmer findByParam(@Param("name") String name, @Param("age") int age);

}
```

在使用 Spring Data JPA 时你甚至可以不用写 SQL 语句，只需要在定义方法名时满足 Spring 的规范即可，Spring 会自动将这些方法按照其命名转换为对应的 SQL 语句，以下是其转换对照表：

| Keyword             | Sample                                                       | JPQL snippet                                                 |
| ------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| `And`               | `findByLastnameAndFirstname`                                 | `… where x.lastname = ?1 and x.firstname = ?2`               |
| `Or`                | `findByLastnameOrFirstname`                                  | `… where x.lastname = ?1 or x.firstname = ?2`                |
| `Is,Equals`         | `findByFirstname`,`findByFirstnameIs`,<br>`findByFirstnameEquals` | `… where x.firstname = ?1`                                   |
| `Between`           | `findByStartDateBetween`                                     | `… where x.startDate between ?1 and ?2`                      |
| `LessThan`          | `findByAgeLessThan`                                          | `… where x.age < ?1`                                         |
| `LessThanEqual`     | `findByAgeLessThanEqual`                                     | `… where x.age <= ?1`                                        |
| `GreaterThan`       | `findByAgeGreaterThan`                                       | `… where x.age > ?1`                                         |
| `GreaterThanEqual`  | `findByAgeGreaterThanEqual`                                  | `… where x.age >= ?1`                                        |
| `After`             | `findByStartDateAfter`                                       | `… where x.startDate > ?1`                                   |
| `Before`            | `findByStartDateBefore`                                      | `… where x.startDate < ?1`                                   |
| `IsNull`            | `findByAgeIsNull`                                            | `… where x.age is null`                                      |
| `IsNotNull,NotNull` | `findByAge(Is)NotNull`                                       | `… where x.age not null`                                     |
| `Like`              | `findByFirstnameLike`                                        | `… where x.firstname like ?1`                                |
| `NotLike`           | `findByFirstnameNotLike`                                     | `… where x.firstname not like ?1`                            |
| `StartingWith`      | `findByFirstnameStartingWith`                                | `… where x.firstname like ?1`(parameter bound with appended `%`) |
| `EndingWith`        | `findByFirstnameEndingWith`                                  | `… where x.firstname like ?1`(parameter bound with prepended `%`) |
| `Containing`        | `findByFirstnameContaining`                                  | `… where x.firstname like ?1`(parameter bound wrapped in `%`) |
| `OrderBy`           | `findByAgeOrderByLastnameDesc`                               | `… where x.age = ?1 order by x.lastname desc`                |
| `Not`               | `findByLastnameNot`                                          | `… where x.lastname <> ?1`                                   |
| `In`                | `findByAgeIn(Collection<Age> ages)`                          | `… where x.age in ?1`                                        |
| `NotIn`             | `findByAgeNotIn(Collection<Age> ages)`                       | `… where x.age not in ?1`                                    |
| `True`              | `findByActiveTrue()`                                         | `… where x.active = true`                                    |
| `False`             | `findByActiveFalse()`                                        | `… where x.active = false`                                   |
| `IgnoreCase`        | `findByFirstnameIgnoreCase`                                  | `… where UPPER(x.firstame) = UPPER(?1)`                      |

### 2.3  单元测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataJPATests {

    @Autowired
    private ProgRepository repository;

    /**
     * 保存数据测试
     */
    @Test
    public void save() {
        // 保存单条数据
        repository.save(new Programmer("pro01", 12, 2121.34f, new Date()));
        // 保存多条数据
        List<Programmer> programmers = new ArrayList<>();
        programmers.add(new Programmer("pro02", 22, 3221.34f, new Date()));
        programmers.add(new Programmer("pro03", 32, 3321.34f, new Date()));
        programmers.add(new Programmer("pro04", 44, 4561.34f, new Date()));
        programmers.add(new Programmer("pro01", 44, 4561.34f, new Date()));
        repository.saveAll(programmers);
    }


    /**
     * 查询数据测试
     */
    @Test
    public void get() {

        // 遵循命名规范的查询
        List<Programmer> programmers = repository.findAllByName("pro01");
        programmers.forEach(System.out::println);

        // 传入参数名称
        Programmer param = repository.findByParam("pro02", 22);
        System.out.println("findByParam:" + param);

        // 占位符查询
        List<Programmer> byCondition = repository.findByConditionAndOrder("pro03", 3321.34f, Sort.Order.asc("salary"));
        System.out.println("byCondition:" + byCondition);

        //条件与分页查询 需要注意的是这里的页数是从第 0 页开始计算的
        Page<Programmer> page = repository.findAll(PageRequest.of(0, 10, Sort.Direction.DESC, "salary"));
        page.get().forEach(System.out::println);
    }


    /**
     * 更新数据测试
     */
    @Test
    public void update() {
        // 保存主键相同的数据就认为是更新操作
        repository.save(new Programmer(1, "updatePro01", 12, 2121.34f, new Date()));
        Optional<Programmer> programmer = repository.findById(1);
        Assert.assertEquals(programmer.get().getName(), "updatePro01");
    }

    /**
     * 删除数据测试
     */
    @Test
    public void delete() {
        Optional<Programmer> programmer = repository.findById(2);
        if (programmer.isPresent()) {
            repository.deleteById(2);
        }
        Assert.assertFalse(programmer.isPresent());
    }
}
```

