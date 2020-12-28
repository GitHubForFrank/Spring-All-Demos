package com.zmz.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author ASNPHDG
 * @create 2020-01-26 22:01
 */
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
