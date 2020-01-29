package com.zmz.app.api.controller;

import com.zmz.app.api.dto.UserDto;
import com.zmz.app.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:59
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping(value = "queryAll")
    public UserDto queryAllUser() {
        log.info("开始执行queryAllUser");
        UserDto userDto = new UserDto();
        userDto.setModelList(userService.queryAllUser());
        return userDto;
    }

}
