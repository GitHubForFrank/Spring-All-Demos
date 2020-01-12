package com.zmz.application.service.impl;

import com.zmz.application.service.UserService;
import com.zmz.domain.model.UserModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:51
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserModel> queryAllUser() {
        List<UserModel> list = new ArrayList<>();

        UserModel model01 = new UserModel();
        model01.setId(1);
        model01.setName("张三");
        list.add(model01);

        UserModel model02 = new UserModel();
        model02.setId(2);
        model02.setName("Frank");
        list.add(model02);

        return list;
    }

}
