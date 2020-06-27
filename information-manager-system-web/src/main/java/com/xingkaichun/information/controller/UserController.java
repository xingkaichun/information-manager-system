package com.xingkaichun.information.controller;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.dto.user.response.GetUserInfoResponse;
import com.xingkaichun.information.dto.user.response.LoginResponse;
import com.xingkaichun.information.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value="用户controller",tags={"用户操作接口"})
@Controller
@RequestMapping(value = "/User")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value="添加用户", notes="添加用户")
    @ResponseBody
    @PostMapping("/AddUser")
    public ServiceResult<UserInfo> addUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody UserDto userDto){
        try {
            ServiceResult<UserInfo> userInfoServiceResult = userService.addUser(httpServletRequest,httpServletResponse,userDto);
            return userInfoServiceResult;
        } catch (Exception e) {
            String message = "新增用户出错";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="登录", notes="登录")
    @ResponseBody
    @PostMapping("/Login")
    public ServiceResult<LoginResponse> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,@RequestBody LoginRequest loginRequest){

        try {
            ServiceResult<LoginResponse> serviceResult = userService.login(httpServletRequest,httpServletResponse,loginRequest);
            return serviceResult;
        } catch (Exception e) {
            String message = "用户登陆失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        }
    }

    @ApiOperation(value="获取登录用户的信息", notes="获取登录用户的信息")
    @ResponseBody
    @PostMapping("/GetUserInfo")
    public ServiceResult<GetUserInfoResponse> getUserInfo(HttpServletRequest request){

        try {
            UserInfo userInfo = userService.getUserInfoBySession(request);
            if(userInfo == null){
                return FreshServiceResult.createFailFreshServiceResult("获取用户信息失败,用户未登录。");
            }

            GetUserInfoResponse getUserInfoResponse =  new GetUserInfoResponse();
            getUserInfoResponse.setUserInfo(userInfo);

            return ServiceResult.createSuccessServiceResult("成功获取用户信息",getUserInfoResponse);
        } catch (Exception e) {
            String message = "获取用户信息失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
    }
}