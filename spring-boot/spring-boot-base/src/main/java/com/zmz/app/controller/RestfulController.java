package com.zmz.app.controller;

import com.zmz.app.bean.Programmer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-27 15:15
 * @description : restful 控制器
 */
@RestController
@RequestMapping("restful")
public class RestfulController {

    @GetMapping("programmers")
    private List<Programmer> getProgrammers() {
        List<Programmer> programmers = new ArrayList<>();
        programmers.add(new Programmer("restful-xiaoming", 12, 100000.00f, LocalDate.of(2019, Month.AUGUST, 2)));
        programmers.add(new Programmer("restful-xiaohong", 23, 900000.00f, LocalDate.of(2013, Month.FEBRUARY, 2)));
        return programmers;
    }
}
