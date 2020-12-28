package com.zmz.core.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 学习网址 ： https://www.cnblogs.com/liantdev/p/10125284.html
 * @author ASNPHDG
 * @create 2020-01-17 21:58
 */
@Component
@Aspect
public class ServiceAop {

    @Pointcut("execution(* com.zmz.app.service.impl.*Impl.*(..))")
    public void matchAll() {}

    @Before("matchAll()")
    public void before() {
        System.out.println("before 前置通知......");
    }

    @Around("matchAll()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object result = null;
        System.out.println("在切入点执行前运行");
        try{
            //获取参数
            result = joinPoint.proceed(joinPoint.getArgs());
            System.out.println("after 在切入点执行后运行,result = " + result);
        } catch (Throwable e) {
            System.out.println("after 在切入点执行后抛出exception运行");
            e.printStackTrace();
        } finally {
            System.out.println("finally......");
        }
        return result;
    }

    @After("matchAll()")
    public void after() {
        System.out.println("after 最后通知......");
    }

}
