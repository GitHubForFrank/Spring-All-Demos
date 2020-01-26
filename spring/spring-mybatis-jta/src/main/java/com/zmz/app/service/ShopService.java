package com.zmz.app.service;

import com.zmz.app.infrastructure.dao.entity.Shop;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-26 8:14
 */
public interface ShopService {
    List<Shop> findAllShop();
    List<Shop> findAllShop2();
    void updateShop(Shop shop);
    void updateShop2(Shop shop);

    void updateJta();
}
