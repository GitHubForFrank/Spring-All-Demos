package com.zmz.app.service.impl;

import com.zmz.app.infrastructure.dao.entity.Shop;
import com.zmz.app.infrastructure.dao.mapper.ShopMapper;
import com.zmz.app.service.ShopService;
import com.zmz.core.annotation.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-26 8:17
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopMapper shopMapper;

    @DataSource("ds_1")
    @Override
    public List<Shop> findAllShop() {
        return shopMapper.findAllShop();
    }

    @DataSource("ds_2")
    @Override
    public List<Shop> findAllShop2() {
        return shopMapper.findAllShop2();
    }

    @DataSource("ds_1")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShop(Shop shop){
        try{
            String title = shop.getTitle();
            if(title.startsWith("tx-s")){
                shopMapper.updateShop(shop);
            }else if(title.startsWith("tx-f")){
                shopMapper.updateShop(shop);
                int s = 1/0;
            }else {
                shopMapper.updateShop(shop);
            }
        }catch(Exception e){
            e.printStackTrace();
            //抛出Exception异常
            throw new RuntimeException  ("人为产生异常");
        }

    }

    @DataSource("ds_2")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateShop2(Shop shop){
        try{
            String title = shop.getTitle();
            if(title.startsWith("tx-s")){
                shopMapper.updateShop2(shop);
            }else if(title.startsWith("tx-f")){
                shopMapper.updateShop2(shop);
                int s = 1/0;
            }else {
                shopMapper.updateShop2(shop);
            }
        }catch(Exception e){
            e.printStackTrace();
            //抛出Exception异常
            throw new RuntimeException  ("人为产生异常");
        }


    }

    /**
     * 如下方法是个坑,Spring AOP无法拦截内部方法调用
     * https://www.cnblogs.com/jimmyshan-study/p/11322508.html
     */
    @Override
    public void updateJta(){
        updateShop(new Shop(1,"tx-title001"));
        updateShop2(new Shop(1,"tx-title002"));
    }

}
