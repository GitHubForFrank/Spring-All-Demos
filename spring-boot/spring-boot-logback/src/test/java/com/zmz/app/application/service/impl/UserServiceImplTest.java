package com.zmz.app.application.service.impl;

import com.zmz.app.SpringBootLogbackApplication;
import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author ASNPHDG
 * @create 2020-01-04 18:02
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void test_query() {
        log.debug("==========================================");
        List<UserModel> list = userService.queryAllUser();
        log.info("test_query 查询结果:{}",list.size());
        log.debug("==========================================");
    }

}