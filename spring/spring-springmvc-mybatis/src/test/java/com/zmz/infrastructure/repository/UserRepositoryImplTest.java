package com.zmz.infrastructure.repository;

import com.zmz.domain.model.UserModel;
import com.zmz.domain.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


/**
 * @author ASNPHDG
 * @create 2020-01-04 19:03
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void can_run_create() {
        UserModel userModel = new UserModel();
        userModel.setName("Name01");
        userModel.setDept("Dept01");
        userModel.setPhone("Phone01");
        userModel.setWebsite("Websit01");
        userRepository.create(userModel);
    }

    @Test
    public void can_run_delete() {
        userRepository.delete(2L);
    }

    @Test
    public void can_run_queryById() {
        UserModel userModel = userRepository.queryById(1L);
        System.out.println(userModel.toString());
    }

    @Test
    public void can_run_updateUser() {
        UserModel userModel = userRepository.queryById(1L);
        userModel.setWebsite(userModel.getWebsite()+"00");
        userRepository.updateUser(userModel);
    }

    @Test
    public void can_run_queryAllUser() {
        List<UserModel> list = userRepository.queryAllUser();
        System.out.println(list.size());
    }

}