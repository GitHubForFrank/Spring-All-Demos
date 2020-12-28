package com.zmz.app.application.service.impl;

import com.zmz.app.SpringBootDruidMybatisApplication;
import com.zmz.app.application.service.ProgrammerService;
import com.zmz.app.domain.model.ProgrammerModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-28 23:43
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={SpringBootDruidMybatisApplication.class})
public class ProgrammerServiceImplTest {

    @Autowired
    private ProgrammerService programmerService;

    @Test
    public void test_query(){
        System.out.println("======================================");
        System.out.println("开始测试 selectAll(使用 Mybatis Plus 方式)......");
        List<ProgrammerModel> list = programmerService.selectAll();

        if(list==null){
            System.out.println("未查询到数据。。。");
            return;
        }

        for (ProgrammerModel programmerModel : list) {
            System.out.println(programmerModel.toString());
        }

        System.out.println("======================================");
        System.out.println("开始测试 selectById(使用 Mybatis Plus 方式 )......");
        ProgrammerModel programmerModel = programmerService.selectById(list.get(0).getId());
        System.out.println(programmerModel.toString());
    }

//    @Test
    public void test_save(){
        ProgrammerModel programmerModel = new ProgrammerModel("xiaoming",18,1000f,new Date());
        programmerService.save(programmerModel);
    }


}