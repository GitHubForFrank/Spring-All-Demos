package com.zmz.mybatis.infrastructure.repository;

import com.zmz.mybatis.domain.repository.UserRepository;
import com.zmz.mybatis.infrastructure.dao.entity.User;
import org.junit.Test;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-03 22:56
 */
public class UserRepositoryImplTest {

    @Test
    public void can_run_selectByPrimaryKey() {
        UserRepository userRepository = new UserRepositoryImpl();
        User user = userRepository.selectByPrimaryKey(34L);
        System.out.println(user.toString());
    }

    @Test
    public void can_run_queryAllUser() {
        UserRepository userRepository = new UserRepositoryImpl();
        List<User> list = userRepository.queryAllUser();
        System.out.println(list.size());
    }


}