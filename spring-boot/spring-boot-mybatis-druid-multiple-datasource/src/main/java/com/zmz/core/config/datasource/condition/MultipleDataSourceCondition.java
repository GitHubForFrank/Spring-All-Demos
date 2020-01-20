package com.zmz.core.config.datasource.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-15 3:01 PM
 */
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
