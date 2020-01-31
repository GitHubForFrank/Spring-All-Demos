package com.zmz.app.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ASNPHDG
 * @create 2020-01-31 10:14
 */
@RestController
public class UserController {

    @GetMapping
    private String index() {
        return "test UserController";
    }

}
