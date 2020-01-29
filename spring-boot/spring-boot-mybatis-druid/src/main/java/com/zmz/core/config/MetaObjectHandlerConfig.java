package com.zmz.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 此类是扩展了MybatisPlus的接口MetaObjectHandler来实现以下情景：
 * 使用Mybatis进行数据库操作的时候，需要对某些公用的字段进行控制，例如 createTime 和 lastUpdateTime
 *
 * 当考虑存储 userId等信息的时候，可以考虑使用 org.slf4j.MDC， refer : http://www.slf4j.org/api/org/slf4j/MDC.html
 * MDC (Mapped Diagnostic Context), MDC顾名思义提供日志的上下文信息，通过MDC中的参数值区分输出的日志。
 * SLF4J的MDC实质上就是一个Map。通常实现SLF4J的日志系统支持MDC，即表明该日志系统负责维护这个Map。应用就可以依赖于日志系统，直接存取key/value对到该Map中。
 * 在SLF4J API中，也提供了MDC的默认实现类org.slf4j.MDC。如果实现SLF4J API的日志系统也支持MDC功能，则覆盖org.slf4j.MDC类。
 * 目前，只有Log4j和Logback支持MDC功能。Java Logging不支持MDC功能，可以通过SLF4J API中的org.slf4j.helpers.BasicMDCAdapter提供MDC功能。
 * 而对于其他的日志系统（如slf4j-simple, slf4j-nop），只能使用SLF4J API中的org.slf4j.helpers.NOPMDCAdapter提供MDC功能。
 *
 * @author ASNPHDG
 * @create 2020-01-28 22:48
 */
@Slf4j
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("插入记录，填写实体");
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        setFieldValByName("createTime", createTime, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject){
        log.debug("更新记录，填写实体");
        Timestamp updateTime = new Timestamp(System.currentTimeMillis());
        setFieldValByName("lastUpdateTime", updateTime, metaObject);
    }


}
