package com.zmz.mybatis.infrastructure.repository;

import com.zmz.mybatis.domain.model.UserModel;
import com.zmz.mybatis.domain.repository.UserRepository;
import org.junit.Test;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:56
 */
public class UserRepositoryImplTest {

    @Test
    public void can_run_create() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserModel userModel = new UserModel();
        userModel.setName("Name01");
        userModel.setDept("Dept01");
        userModel.setPhone("Phone01");
        userModel.setWebsite("Websit01");
        userRepository.create(userModel);
    }

    @Test
    public void can_run_delete() {
        UserRepository userRepository = new UserRepositoryImpl();
        userRepository.delete(2L);
    }

    @Test
    public void can_run_queryById() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserModel userModel = userRepository.queryById(1L);
        System.out.println(userModel.toString());
    }

    @Test
    public void can_run_updateUser() {
        UserRepository userRepository = new UserRepositoryImpl();
        UserModel userModel = userRepository.queryById(1L);
        userModel.setWebsite(userModel.getWebsite()+"00");
        userRepository.updateUser(userModel);
    }

    @Test
    public void can_run_queryAllUser() {
        UserRepository userRepository = new UserRepositoryImpl();
        List<UserModel> list = userRepository.queryAllUser();
        System.out.println(list.size());
    }

}