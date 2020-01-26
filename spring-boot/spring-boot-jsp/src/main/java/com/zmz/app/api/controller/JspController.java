package com.zmz.app.api.controller;

import com.zmz.app.api.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

/**
 * @author ASNPHDG
 * @create 2020-01-26 20:27
 */
@Slf4j
@Controller
@RequestMapping("index")
public class JspController {

    @RequestMapping
    public String jsp(Model model){
        UserDto userDto = new UserDto("Frank", 18, 1000.21f, LocalDate.now());
        model.addAttribute("userDto",userDto);
        log.info(userDto.toString());
        return "show";
    }

}
