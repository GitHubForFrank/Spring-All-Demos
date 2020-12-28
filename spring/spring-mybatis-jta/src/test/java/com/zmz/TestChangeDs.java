package com.zmz;

import com.zmz.app.infrastructure.dao.entity.Shop;
import com.zmz.app.service.ShopService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-25 14:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
public class TestChangeDs {

    @Resource
    private ShopService shopService;

    /**
     * 测试正常执行查询
     */
    @Test
    public void test_findAllShop_ds01_ds02(){
        List<Shop> shopList01 = shopService.findAllShop();
        System.out.println(shopList01.get(0).toString());
        List<Shop> shopList02 = shopService.findAllShop2();
        System.out.println(shopList02.get(0).toString());
    }

    /**
     * 测试无事务情况，正常update
     */
    @Test
    public void test_updateShop_ds01_ds02(){
        shopService.updateShop(new Shop(1,"title0001"));
        shopService.updateShop2(new Shop(1,"title0002"));
    }

    /**
     * 测试分布式事务成功的情况
     */
    @Test
    public void test_JTA_success(){
        shopService.updateShop(new Shop(1,"tx-s-title001"));
        shopService.updateShop2(new Shop(1,"tx-s-title002"));
    	System.out.println("SUCCESS JTA");
    }

    /**
     * 测试分布式事务失败的情况
     * 数据源1的事务提交成功
     * 数据源2的事务回滚
     */
    @Test
    public void test_JTA_Fail_01(){
        shopService.updateShop(new Shop(1,"tx-s-title001"));
        shopService.updateShop2(new Shop(1,"tx-f-title002"));
        System.out.println("Fail JTA");
    }

    /**
     * 测试分布式事务失败的情况
     * 数据源1的事务回滚
     * 数据源2的事务未开启
     */
    @Test
    public void test_JTA_Fail_02(){
        shopService.updateShop(new Shop(1,"tx-f-title001"));
        shopService.updateShop2(new Shop(1,"tx-f-title002"));
        System.out.println("Fail JTA");
    }

}
