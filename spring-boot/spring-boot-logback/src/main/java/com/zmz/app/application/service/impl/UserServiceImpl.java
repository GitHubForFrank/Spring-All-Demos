package com.zmz.app.application.service.impl;

import com.zmz.app.application.service.UserService;
import com.zmz.app.domain.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:51
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserModel> queryAllUser() {
        log.info("开始执行UserServiceImpl.queryAllUser ......");
        List<UserModel> list = new ArrayList<>();
        UserModel model01 = new UserModel("xiaoming-01","IT","123456","abc");
        UserModel model02 = new UserModel("xiaoming-02","IT","123456","abc");
        list.add(model01);
        list.add(model02);
        return list;
    }

}
