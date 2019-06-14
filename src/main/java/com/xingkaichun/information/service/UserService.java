package com.xingkaichun.information.service;

import com.github.pagehelper.PageInfo;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.dto.user.response.LoginResponse;
import com.xingkaichun.information.model.UserDomain;

import java.util.List;


/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    int addUser(UserDto userDto);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);

    List<UserDomain> queryUser(UserDomain userDomain);

    UserDomain queryOneUserByUserId(String userId);
    UserDomain queryOneUserByEmail(String email);
    UserDomain queryOneUserByUserName(String userName);
    UserDomain queryOneUserByPhone(String phone);
    UserDomain queryOneUserByUserToken(String userToken);

    UserDomain login(LoginRequest loginRequest);

    int updateUserToken(UserDomain userDomain);
}
