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
 * @description : 测试单数据库事务
 */
@RestController
public class TransactionController {

    @Autowired
    private ProgrammerMapper programmerDao;


    @RequestMapping("db1/change")
    @Transactional
    public void changeDb1() {
        Programmer programmer = new Programmer(1, "db1", 99, 6662.32f, new Date());
        programmerDao.modify(Data.DATASOURCE1, programmer);
    }

    @RequestMapping("ts/db1/change")
    @Transactional
    public void changeTsDb1() {
        Programmer programmer = new Programmer(1, "db1", 88, 6662.32f, new Date());
        programmerDao.modify(Data.DATASOURCE1, programmer);
        // 抛出异常 查看回滚
        int j = 1 / 0;
    }

}
