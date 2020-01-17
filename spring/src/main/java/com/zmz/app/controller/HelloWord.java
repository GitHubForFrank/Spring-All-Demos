package com.zmz.app.controller;

/**
 * @author ASNPHDG
 * @create 2020-01-04 16:15
 */
public class HelloWord {

    public String name;

    public void setName(String name) {
        this.name = name;
    }

    public void hello() {
        System.out.print("hello:" + name);
    }


}
