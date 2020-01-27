package com.zmz.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author ASNPHDG
 * @create 2020-01-27 17:34
 */
@Slf4j
@Controller
public class LoginController {

    @PostMapping("login")
    public String login(String username, String chatPageType, HttpSession session) {
        log.info("username {},chatPageType {}",username,chatPageType);
        //多种聊天页面的时候用chatPageType做区分
        session.setAttribute("username", username);
        return "chat";
    }

    @GetMapping
    public String index() {
        return "index";
    }

}
