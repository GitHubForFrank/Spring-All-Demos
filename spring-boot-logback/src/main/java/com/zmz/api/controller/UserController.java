package com.zmz.api.controller;

import com.zmz.api.dto.UserDto;
import com.zmz.application.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * application.properties 不要设置端口为 10100 和context-path 为 spring-boot-logback，
 * 用浏览器直接打开如下URL访问,有数据返回意味着数据交互成功
 * http://localhost:10100/spring-boot-logback/user/query
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
    @GetMapping(value = "query")
    public UserDto queryAllUser() {
        UserDto userDto = new UserDto();
        userDto.setModelList(userService.queryAllUser());
        return userDto;
    }

    @ResponseBody
    @GetMapping(value = "query/{userId}")
    public UserDto queryUserById(@PathVariable("userId") Long userId) {
        System.out.println(userId);
        UserDto userDto = new UserDto();
        userDto.setModelList(userService.queryAllUser());
        return userDto;
    }

}
