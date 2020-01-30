package com.zmz.app.controller;

import com.zmz.app.bean.Programmer;
import com.zmz.app.constant.Data;
import com.zmz.app.dao.ProgrammerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author ASNPHDG
 * @create 2020-01-30 18:47
 * @description : 测试分布式事务
 */
@RestController
public class XATransactionController {

    @Autowired
    private ProgrammerMapper programmerDao;


    @RequestMapping("/db/change")
    @Transactional
    public void changeDb() {
        Programmer programmer01 = new Programmer(1, "db1", 100, 6662.32f, new Date());
        Programmer programmer02 = new Programmer(1, "db2", 100, 6662.32f, new Date());
        programmerDao.modify(Data.DATASOURCE1, programmer01);
        programmerDao.modify(Data.DATASOURCE2, programmer02);
    }

    @RequestMapping("ts/db/change")
    @Transactional
    public void changeTsDb() {
        Programmer programmer01 = new Programmer(1, "db1", 99, 6662.32f, new Date());
        Programmer programmer02 = new Programmer(1, "db2", 99, 6662.32f, new Date());
        programmerDao.modify(Data.DATASOURCE1, programmer01);
        programmerDao.modify(Data.DATASOURCE2, programmer02);
        int i = 1 / 0;
    }


}
