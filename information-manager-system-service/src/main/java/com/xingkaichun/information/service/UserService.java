package com.xingkaichun.information.service;

import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.dto.user.response.LoginResponse;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    ServiceResult<UserInfo> addUser(HttpServletRequest request, HttpServletResponse response, UserDto userDto);
    ServiceResult<LoginResponse> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LoginRequest loginRequest);

    UserInfo getUserInfoBySession(HttpServletRequest request);
    void updateUserSession(ServletRequest request);

    UserInfo queryUserByUserId(String userId);
}
