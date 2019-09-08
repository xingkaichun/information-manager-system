package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceCode;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.dto.user.response.GetUserInfoResponse;
import com.xingkaichun.information.dto.user.response.LoginResponse;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsCookie;
import com.xingkaichun.utils.CommonUtilsMd5;
import com.xingkaichun.utils.CommonUtilsSession;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
    public FreshServiceResult addUser(HttpServletRequest request, HttpServletResponse response,@RequestBody UserDto userDto){

        try {
            if(CommonUtils.isNUllOrEmpty(userDto.getPhone())){
                return FreshServiceResult.createFailFreshServiceResult("手机号不能为空");
            }
            ServiceResult<UserInfo> userInfoServiceResult = userService.addUser(userDto);
            if(userInfoServiceResult.getServiceCode()== ServiceCode.FAIL){
                return FreshServiceResult.createFailFreshServiceResult(userInfoServiceResult.getMessage());
            }
            UserInfo userInfo = userInfoServiceResult.getResult();
            UserDomain userDomain = userService.queryOneUserByUserId(userInfo.getUserId());

            CommonUtilsSession.saveUser(request,userDomain);

            userDomain.setUserToken(generateUserToken());
            userService.updateUserToken(userDomain);
            CommonUtilsCookie.saveUser(response,userDomain);
        } catch (Exception e) {
            String message = "新增用户出错";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
        return FreshServiceResult.createSuccessFreshServiceResult("新增用户成功");
    }

    private String generatePassword(String password, String passwordSalt) {
        return CommonUtilsMd5.MD5Encode(password+passwordSalt, CharEncoding.UTF_8,false);
    }

    private String generateUserToken() {
        return CommonUtilsMd5.MD5Encode(String.valueOf(UUID.randomUUID())+String.valueOf(UUID.randomUUID()), CharEncoding.UTF_8,false);
    }

    @ApiOperation(value="登录", notes="登录")
    @ResponseBody
    @PostMapping("/Login")
    public ServiceResult<LoginResponse> login(HttpServletRequest request, HttpServletResponse response,@RequestBody LoginRequest loginRequest){

        try {
            if(CommonUtils.isNUllOrEmpty(loginRequest.getEmail())){
                return ServiceResult.createFailServiceResult("邮箱不能为空");
            }
            if(CommonUtils.isNUllOrEmpty(loginRequest.getPassword())){
                return ServiceResult.createFailServiceResult("密码不能为空");
            }

            //检测邮箱是否存在
            ServiceResult<UserInfo> userInfoServiceResult =  userService.queryOneUserByEmail(loginRequest.getEmail());
            if(userInfoServiceResult.getServiceCode()==ServiceCode.FAIL || userInfoServiceResult.getResult()==null){
                return ServiceResult.createFailServiceResult("登陆失败,请检测邮箱与密码");
            }
            //比较密码是否正确
            UserInfo userInfo = userInfoServiceResult.getResult();
            UserDomain userDomain = userService.queryOneUserByUserId(userInfo.getUserId());
            loginRequest.setPassword(generatePassword(loginRequest.getPassword(),userDomain.getPasswordSalt()));
            ServiceResult<UserInfo> ud = userService.queryUserByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
            if(CommonUtils.isNUll(ud)){
                return ServiceResult.createFailServiceResult("登陆失败,请检测邮箱与密码");
            }

            CommonUtilsSession.saveUser(request,userDomain);

            userDomain.setUserToken(generateUserToken());
            userService.updateUserToken(userDomain);
            CommonUtilsCookie.saveUser(response,userDomain);

            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setUserInfo(userInfo);

            return ServiceResult.createSuccessServiceResult("用户登陆成功",loginResponse);
        } catch (Exception e) {
            String message = "用户登陆失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
    }

    @ApiOperation(value="获取登录用户的信息", notes="获取登录用户的信息")
    @ResponseBody
    @PostMapping("/GetUserInfo")
    public ServiceResult<GetUserInfoResponse> getUserInfo(HttpServletRequest request){

        try {
            UserInfo userInfo = userService.getUserInfoBySession(request);
            if(userInfo == null){
                return FreshServiceResult.createFailFreshServiceResult("获取用户信息失败");
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
