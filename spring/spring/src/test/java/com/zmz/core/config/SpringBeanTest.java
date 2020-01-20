package com.zmz.core.config;

import com.zmz.app.controller.DemoController;
import com.zmz.app.controller.HelloWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author ASNPHDG
 * @create 2020-01-04 16:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class SpringBeanTest {

    @Autowired
    private SpringContextUtil springContextUtil;

    @Test
    public void test_01(){
        //HelloWord he = new HelloWord();
        //1.创建spring的ioc容器对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.从ioc容器中获取bean实例
        HelloWord helloWorld = (HelloWord) ctx.getBean("helloWorld");
        helloWorld.hello();
    }

    @Test
    public void test_02(){
        //1.创建spring的ioc容器对象
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //2.从ioc容器中获取bean实例
        DemoController demoController = ctx.getBean(DemoController.class);
        demoController.saySomething();
    }

    @Test
    public void test_03(){
        HelloWord helloWorld = (HelloWord) springContextUtil.getBean("helloWorld");
        helloWorld.hello();
    }

    @Test
    public void test_04(){
        DemoController demoController =(DemoController) springContextUtil.getBean(DemoController.class);
        demoController.saySomething();
    }

}