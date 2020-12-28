package com.zmz.api.controller;

import com.zmz.application.service.UserService;
import com.zmz.domain.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 19:46
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/allUser")
    public String list(Model model) {
        List<UserModel> list = userService.queryAllUser();
        model.addAttribute("list", list);
        return "allUser";
    }

    @RequestMapping("toAddUser")
    public String toAddUser() {
        return "addUser";
    }

    @RequestMapping("/addUser")
    public String addUser(UserModel userModel) {
        userService.create(userModel);
        return "redirect:/user/allUser";
    }

    @RequestMapping("/del/{userId}")
    public String deleteUser(@PathVariable("userId") Long id) {
        userService.delete(id);
        return "redirect:/user/allUser";
    }

    @RequestMapping("toUpdateUser")
    public String toUpdatePaper(Model model, Long id) {
        model.addAttribute("user", userService.queryById(id));
        return "updateUser";
    }

    @RequestMapping("/updateUser")
    public String updateUser(UserModel userModel) {
        userService.updateUser(userModel);
        return "redirect:/user/allUser";
    }
}
