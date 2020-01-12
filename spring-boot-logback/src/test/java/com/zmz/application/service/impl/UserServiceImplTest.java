package com.zmz.application.service.impl;

import com.zmz.Application;
import com.zmz.application.service.UserService;
import com.zmz.domain.model.UserModel;
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
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={Application.class})
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void test_can_query() {
        List<UserModel > list = userService.queryAllUser();
        System.out.println(list.size());
    }

}