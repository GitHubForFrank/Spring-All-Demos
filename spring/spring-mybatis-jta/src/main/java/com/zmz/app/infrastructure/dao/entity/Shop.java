package com.zmz.app.infrastructure.dao.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ASNPHDG
 * @create 2020-01-25 14:18
 */
@Setter
@Getter
public class Shop {

    /** 主键id */
    private Integer id;

    /** 外部平台店铺id */
    private String shopId;

    /** 店铺所属类目id */
    private String catId;

    /** 卖家昵称 */
    private String nick;

    /** 店铺标题 */
    private String title;

    /** 店铺描述 */
    private String description;

    public Shop() {
    }

    public Shop(Integer id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", catId='" + catId + '\'' +
                ", nick='" + nick + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
