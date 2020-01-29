package com.zmz.app.application.service;

import com.zmz.app.domain.model.UserModel;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:51
 */
public interface UserService {
    List<UserModel> queryAllUser();
}
