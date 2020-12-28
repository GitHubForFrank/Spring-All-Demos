package com.zmz.app.infrastructure.dao.mapper;

import com.zmz.app.infrastructure.dao.entity.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-25 14:18
 */
@Mapper
public interface ShopMapper {

    List<Shop> findAllShop();
    void updateShop(Shop shop);

    List<Shop> findAllShop2();
    void updateShop2(Shop shop);

}
