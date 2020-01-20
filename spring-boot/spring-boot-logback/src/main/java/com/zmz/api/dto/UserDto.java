package com.zmz.api.dto;

import com.zmz.domain.model.UserModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author ASNPHDG
 * @create 2020-01-04 17:54
 */
@Setter
@Getter
public class UserDto {

    List<UserModel> modelList;

}
