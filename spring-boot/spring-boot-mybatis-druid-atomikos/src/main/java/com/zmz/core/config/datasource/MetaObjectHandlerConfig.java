package com.zmz.core.config.datasource;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * 对于Entity的一些通用字段，例如update time和create id等，可以在这个类统一设值
 * @author ASNPHDG
 * @create 2020-01-13 3:59 PM
 */
@Slf4j
@Component
public class MetaObjectHandlerConfig implements MetaObjectHandler {

  @Override
  public void insertFill(MetaObject metaObject) {
    log.debug("Insert record, fill in entity");
    Timestamp createTime = new Timestamp(System.currentTimeMillis());

    setFieldValByName("createTime", createTime, metaObject);
    setFieldValByName("lastUpdateTime", createTime, metaObject);
  }

  @Override
  public void updateFill(MetaObject metaObject){
    log.debug("Update record, fill in entity");
    Timestamp updateTime = new Timestamp(System.currentTimeMillis());

    setFieldValByName("lastUpdateTime", updateTime, metaObject);
  }

}
