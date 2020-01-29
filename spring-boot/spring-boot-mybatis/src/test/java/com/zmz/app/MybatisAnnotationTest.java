package com.zmz.app;

import com.zmz.app.bean.Programmer;
import com.zmz.app.dao.ProgrammerDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author ASNPHDG
 * @create 2020-01-29 17:57
 * @description: 注解Sql测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisAnnotationTest {

    @Autowired
    private ProgrammerDao programmerDao;

    @Test
    public void save() {
        programmerDao.save(new Programmer("xiaominng", 12, 3467.34f, new Date()));
        programmerDao.save(new Programmer("xiaominng", 12, 3467.34f, new Date()));
    }

    @Test
    public void modify() {
        programmerDao.modify(new Programmer(1, "xiaolan", 21, 347.34f, new Date()));
    }

    @Test
    public void selectByCondition() {
        Programmer programmers = programmerDao.selectById(1);
        System.out.println(programmers);
    }

    @Test
    public void delete() {
        programmerDao.delete(3);
        Programmer programmers = programmerDao.selectById(3);
        Assert.assertNull(programmers);
    }

}

