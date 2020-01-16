package com.zmz.app.infrastructure.repository.sqlserver;

import com.zmz.app.Application;
import com.zmz.app.domain.model.UserModel;
import com.zmz.app.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-13 1:08 PM
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT, classes={Application.class})
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_can_query(){
        List<UserModel> list = userRepository.queryAllUser();
        for (UserModel userModel : list) {
            System.out.println(userModel.toString());
        }
    }

    @Test
    public void test_can_create(){
        UserModel userModel = new UserModel();
        userModel.setName("99999");
        userModel.setDept("123");
        userModel.setPhone("1");
        userModel.setWebsite("123");
        userRepository.create(userModel);
    }

}