package com.zmz.app.controller;

import com.zmz.core.config.Programmer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ASNPHDG
 * @create 2020-01-28 17:04
 */
@RestController
@RequestMapping("yml")
public class YmlController {

    @Autowired
    private Programmer programmer;

    @RequestMapping
    public Programmer programmer(){
        return programmer;
    }
}
