package com.xingkaichun.information.service;

import com.github.pagehelper.PageInfo;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.model.UserDomain;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    ServiceResult<UserInfo> addUser(UserDto userDto);
    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);

    UserDomain queryOneUserByUserId(String userId);
    UserInfo queryOneUserByUserId2(String userId);
    ServiceResult<UserInfo> queryOneUserByEmail(String email);
    UserDomain queryOneUserByUserToken(String userToken);

    ServiceResult<UserInfo> queryUserByEmailAndPassword(String email, String password);

    int updateUserToken(UserDomain userDomain);

    UserInfo getUserInfoBySession(HttpServletRequest request);
}
