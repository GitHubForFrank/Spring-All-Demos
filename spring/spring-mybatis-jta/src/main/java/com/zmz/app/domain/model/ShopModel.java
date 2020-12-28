package com.zmz.app.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ASNPHDG
 * @create 2020-01-26 8:19
 */
@Setter
@Getter
public class ShopModel {

    private Integer id;
    private String shopId;
    private String catId;
    private String nick;
    private String title;
    private String description;


    @Override
    public String toString() {
        return "ShopModel{" +
                "id=" + id +
                ", shopId='" + shopId + '\'' +
                ", catId='" + catId + '\'' +
                ", nick='" + nick + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
