package com.zmz.app.controller;

import com.zmz.app.bean.Programmer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-27 15:15
 * @description : 跳转渲染模板引擎 默认模板的存放位置为classpath:templates
 */
@Controller
@RequestMapping("freemarker")
public class FreeMarkerController {

    @RequestMapping("show")
    private String programmerShow(ModelMap modelMap){
        List<Programmer> programmerList=new ArrayList<>();
        programmerList.add(new Programmer("freemarker-xiaoming",12,100000.00f,LocalDate.of(2019,Month.AUGUST,2)));
        programmerList.add(new Programmer("freemarker-xiaohong",23,900000.00f,LocalDate.of(2013,Month.FEBRUARY,2)));
        modelMap.addAttribute("programmers",programmerList);
        return "markerShow";
    }

}
