package com.zmz.app.infrastructure.repository.sqlserver;

import com.zmz.app.Application;
import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import com.zmz.app.domain.repository.UserRepository01;
import com.zmz.app.domain.repository.UserRepository02;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ASNPHDG
 * @create 2020-01-16 21:28
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={Application.class})
public class DynamicDataSourceIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRepository01 userRepository01;
    @Autowired
    private UserRepository02 userRepository02;

    @Test
    public void test__userRepository(){
        UserModel userModel = new UserModel();
        userModel.setName("00000");
        userModel.setDept("userRepository");
        userRepository.create(userModel);
    }

    @Test
    public void test__userRepository01(){
        UserModel userModel = new UserModel();
        userModel.setName("11111");
        userModel.setDept("userRepository01");
        userRepository01.create(userModel);
    }

    @Test
    public void test__userRepository02(){
        UserModel userModel = new UserModel();
        userModel.setName("22222");
        userModel.setDept("userRepository02");
        userRepository02.create(userModel);
    }

}
