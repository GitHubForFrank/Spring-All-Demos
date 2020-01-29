# Spring Boot 整合 Druid+Mybatis


<nav>
<a href="#一项目说明">一、项目说明</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#11-项目结构">1.1 项目结构</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#12-基本依赖">1.2 基本依赖</a><br/>
<a href="#二整合-Druid-+-Mybatis">二、整合 Druid + Mybatis</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#21-配置数据源">2.1 配置数据源</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#22--整合查询">2.2  整合查询</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#23-监控数据">2.3 监控数据</a><br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#24-Druid-控制台">2.4 Druid 控制台</a><br/>
</nav>

## 一、项目说明

### 1.1 项目结构

1. 项目建表语句如下；

```sql
use pds_demo01;
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS `programmer`;
create table programmer (
  id       int primary key auto_increment,
  name     varchar(20),
  age      tinyint,
  salary   float,
  birthday datetime
)

DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(100) NULL COMMENT '名字',
  `dept` varchar(100) NULL COMMENT '部门',
  `phone` varchar(100) NULL COMMENT '电话',
  `website` varchar(100) NULL COMMENT '网站',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

```

文件放在了 https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/database-scripts

2. 为了演示 Druid 控制台的功能，项目以 Web 的方式构建。

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-mybatis-druid/project-structure.png"/> </div>

### 1.2 基本依赖

按照 Spring 官方对于自定义的 starter 命名规范的要求：

- 官方的 starter 命名：spring-boot-starter-XXXX
- 其他第三方 starter 命名：XXXX-spring-boot-starter 

所以 Mybatis 的 starter 命名为 mybatis-spring-boot-starter，如果有自定义 starter 需求，也需要按照此命名规则进行命名。

此外，此Demo还引入了现在流行的 Mybatis Plus使用。

```xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>1.3.2</version>
</dependency>
<!--引入mysql驱动-->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.13</version>
</dependency>
<!--druid 依赖-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>1.1.10</version>
</dependency>
<dependency>
	<groupId>com.baomidou</groupId>
	<artifactId>mybatis-plus-boot-starter</artifactId>
	<version>3.0.5</version>
</dependency>
```

Spring Boot 与 Mybatis 版本的对应关系：

| MyBatis-Spring-Boot-Starter | [MyBatis-Spring](http://www.mybatis.org/spring/index.html#Requirements) | Spring Boot   |
| --------------------------- | ------------------------------------------------------------ | ------------- |
| **1.3.x (1.3.1)**           | 1.3 or higher                                                | 1.5 or higher |
| **1.2.x (1.2.1)**           | 1.3 or higher                                                | 1.4 or higher |
| **1.1.x (1.1.1)**           | 1.3 or higher                                                | 1.3 or higher |
| **1.0.x (1.0.2)**           | 1.2 or higher                                                | 1.3 or higher |



## 二、整合 Druid + Mybatis

### 2.1 配置数据源

本用例采用 Druid 作为数据库连接池，虽然 Druid 性能略逊于 Hikari，但提供了更为全面的监控管理，可以按照实际需求选用 Druid 或者 Hikari。（关于 Hikari 数据源的配置可以参考 [spring-boot-mybatis 项目](https://github.com/GitHubForFrank/Spring-All-Demos/tree/master/spring-boot/spring-boot-mybatis)）

application.properties

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
logging.file.name=spring-boot-mybatis-druid
logging.file.path=../logs/app

#-----------------------------------------------------------------------------------
#Below is for db only
spring.datasource.url=jdbc:mysql://182.61.149.71:3306/pds_demo01
spring.datasource.username=kong
spring.datasource.password=2020Jan11
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource

spring.datasource.druid.initialSize=5
spring.datasource.druid.minIdle=5
spring.datasource.druid.maxActive=10
spring.datasource.druid.maxWait=60000
spring.datasource.druid.timeBetweenEvictionRunsMillis=60000
spring.datasource.druid.minEvictableIdleTimeMillis=300000
spring.datasource.druid.validationQuery=SELECT 1
spring.datasource.druid.testWhileIdle=true
spring.datasource.druid.testOnBorrow=false
spring.datasource.druid.testOnReturn=false
spring.datasource.druid.removeAbandoned=true
spring.datasource.druid.remove-abandoned-timeout=180

```

mybatis.properties

```properties
mybatis.mapperLocations = classpath:mybatis/mapper/*.xml
mybatis.typeAliasesPackage = com.zmz.app.infrastructure.dao.entity
mybatis.configLocation = classpath:/mybatis/config/mybatis-config.xml

```

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <settings>
        <!-- 使全局的映射器启用或禁用缓存。 -->
        <setting name="cacheEnabled" value="true" />
        <!-- 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载。 -->
        <setting name="lazyLoadingEnabled" value="true" />
        <!-- 当启用时，有延迟加载属性的对象在被调用时将会完全加载任意属性。否则，每种属性将会按需要加载。 -->
        <setting name="aggressiveLazyLoading" value="true"/>
        <!-- 是否允许单条sql 返回多个数据集  (取决于驱动的兼容性) default:true -->
        <setting name="multipleResultSetsEnabled" value="true" />
        <!-- 是否可以使用列的别名 (取决于驱动的兼容性) default:true -->
        <setting name="useColumnLabel" value="true" />
        <!-- 允许JDBC 生成主键。需要驱动器支持。如果设为了true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。  default:false  -->
        <setting name="useGeneratedKeys" value="false" />
        <!-- 指定 MyBatis 如何自动映射 数据基表的列 NONE：不隐射　PARTIAL:部分  FULL:全部  -->
        <setting name="autoMappingBehavior" value="PARTIAL" />
        <!-- 这是默认的执行类型  （SIMPLE: 简单； REUSE: 执行器可能重复使用prepared statements语句；BATCH: 执行器可以重复执行语句和批量更新）  -->
        <setting name="defaultExecutorType" value="SIMPLE" />

        <setting name="defaultStatementTimeout" value="25" />

        <setting name="defaultFetchSize" value="100" />

        <setting name="safeRowBoundsEnabled" value="false" />
        <!-- 使用驼峰命名法转换字段。 -->
        <setting name="mapUnderscoreToCamelCase" value="true" />
        <!-- 设置本地缓存范围 session:就会有数据的共享  statement:语句范围 (这样就不会有数据的共享 ) defalut:session -->
        <setting name="localCacheScope" value="SESSION" />
        <!-- 默认为OTHER,为了解决oracle插入null报错的问题要设置为NULL -->
        <setting name="jdbcTypeForNull" value="NULL" />
        <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString" />
    </settings>
    <databaseIdProvider type="DB_VENDOR">
        <property name="DB2" value="db2"/>
        <property name="Oracle" value="oracle" />
        <property name="Adaptive Server Enterprise" value="sybase"/>
        <property name="MySQL" value="mysql" />
        <property name="PostgreSQL" value="postgresql" />
    </databaseIdProvider>
</configuration>

```

### 2.2  整合查询

采用Mybatis方式

```java
@Mapper
public interface UserMapper {

    @Select("select * from tbl_user where id = #{id,jdbcType=BIGINT}")
    UserEntity selectByPrimaryKey(@Param("id") Long id);

    List<UserEntity> queryAllUser();
    void insert(UserEntity user);

    @Delete("delete from tbl_user where id = #{id,jdbcType=BIGINT}")
    void deleteById(@Param("id") Long id);

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

    <select id="queryAllUser" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from tbl_user
    </select>

</mapper>
```


采用Mybatis Plus方式

```Java
/**
 * 采用Mybatis Plus方式
 * @author ASNPHDG
 * @create 2020-01-28 20:55
 */
@Mapper
public interface ProgrammerMapper extends BaseMapper<ProgrammerEntity> {
}
```

```Java
@Repository
public class ProgrammerRepositoryImpl implements ProgrammerRepository {

    @Resource
    private ProgrammerMapper programmerMapper;
    @Autowired
    private ProgrammerTranslator programmerTranslator;

    @Override
    public List<ProgrammerModel> selectAll() {
        List<ProgrammerEntity> entityList = programmerMapper.selectList(
                new QueryWrapper<ProgrammerEntity>().lambda()
                        .orderByAsc(ProgrammerEntity::getId)
        );
        return programmerTranslator.E2VOs(entityList, null);
    }

    @Override
    public void save(ProgrammerModel programmerModel) {
        programmerMapper.insert(programmerTranslator.VO2E(null,programmerModel));
    }

    @Override
    public ProgrammerModel selectById(int id) {
        ProgrammerEntity entity = programmerMapper.selectOne(
                new QueryWrapper<ProgrammerEntity>().lambda()
                        .eq(ProgrammerEntity::getId, id)
        );
        return programmerTranslator.E2VO(entity,null);
    }

    @Override
    public int modify(ProgrammerModel programmerModel) {
        return programmerMapper.updateById(programmerTranslator.VO2E(null,programmerModel));
    }

    @Override
    public void delete(int id) {
        programmerMapper.deleteById(id);
    }

}
```

### 2.3 监控数据

在 Spring Boot 中可以通过 HTTP 接口将 Druid 的监控数据以  JSON 的形式暴露出去，可以用于健康检查等功能：

```java
@RestController
public class DruidStatController {

    @GetMapping("/stat")
    public Object druidStat() {
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
```

访问地址为 `http://www.bejson.com/`  `http://localhost:10190/app/stat`


<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-mybatis-druid/druid-json.png"/> </div>

### 2.4 Druid 控制台

访问地址为 `http://localhost:10190/app/druid/login.html` ：

<div align="center"> <img src="https://github.com/GitHubForFrank/spring-all-demos/blob/master/00-materials/images/spring-boot-mybatis-druid/druid.png"/> </div>
