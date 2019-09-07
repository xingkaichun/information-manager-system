package com.xingkaichun.information.service;

import com.github.pagehelper.PageInfo;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.model.UserDomain;

import java.util.List;


/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    int addUser(UserDto userDto);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);

    UserDomain queryOneUserByUserId(String userId);
    UserDomain queryOneUserByEmail(String email);
    UserDomain queryOneUserByUserName(String userName);
    UserDomain queryOneUserByPhone(String phone);
    UserDomain queryOneUserByUserToken(String userToken);

    UserDomain queryUserByEmailAndPassword(String email, String password);

    int updateUserToken(UserDomain userDomain);
}
