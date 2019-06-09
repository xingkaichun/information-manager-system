package com.xingkaichun.information.controller;

import com.xingkaichun.information.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.base.ServiceResult;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.response.LoginResponse;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.information.utils.CommonUtils;
import com.xingkaichun.information.utils.CommonUtilsMd5Utils;
import org.apache.commons.lang3.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Controller
@RequestMapping(value = "/User")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/AddUser")
    public FreshServiceResult addUser(@RequestBody UserDto userDto){

        try {
            if(CommonUtils.isNUllOrEmpty(userDto.getEmail())){
                return FreshServiceResult.createFailFreshServiceResult("邮箱不能为空");
            }
            if(CommonUtils.isNUllOrEmpty(userDto.getUserName())){
                return FreshServiceResult.createFailFreshServiceResult("用户名不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(userDto.getUserId())){
                return FreshServiceResult.createFailFreshServiceResult("系统自动分配用户Id,请不要填写");
            }
            if(CommonUtils.isNUllOrEmpty(userDto.getPassword())){
                return FreshServiceResult.createFailFreshServiceResult("密码不能为空");
            }
            if(CommonUtils.isNUllOrEmpty(userDto.getPhone())){
                return FreshServiceResult.createFailFreshServiceResult("手机号不能为空");
            }
            if(!CommonUtils.isNUllOrEmpty(userService.queryUser(new UserDomain(null,userDto.getEmail(),null,null,null,null)))){
                return FreshServiceResult.createFailFreshServiceResult("邮箱已经存在");
            }
            if(!CommonUtils.isNUllOrEmpty(userService.queryUser(new UserDomain(null,null,userDto.getUserName(),null,null,null)))){
                return FreshServiceResult.createFailFreshServiceResult("用户名已经存在");
            }
            if(!CommonUtils.isNUllOrEmpty(userService.queryUser(new UserDomain(null,null,null,null,null,userDto.getPhone())))){
                return FreshServiceResult.createFailFreshServiceResult("手机号已经存在");
            }

            userDto.setUserId(String.valueOf(UUID.randomUUID()));
            userDto.setPasswordSalt(String.valueOf(UUID.randomUUID()));
            String newPassword = generatePassword(userDto.getPassword(), userDto.getPasswordSalt());
            userDto.setPassword(newPassword);

            userService.addUser(userDto);
        } catch (Exception e) {
            String message = "新增用户出错";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
        return FreshServiceResult.createSuccessFreshServiceResult("新增用户成功");
    }

    private String generatePassword(String password, String passwordSalt) {
        return CommonUtilsMd5Utils.MD5Encode(password+passwordSalt, CharEncoding.UTF_8,false);
    }

    @ResponseBody
    @GetMapping("/All")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        return userService.findAllUser(pageNum,pageSize);
    }

    @ResponseBody
    @GetMapping("/Login")
    public ServiceResult<LoginResponse> login(@RequestBody LoginRequest loginRequest){

        try {
            if(CommonUtils.isNUllOrEmpty(loginRequest.getEmail())){
                return ServiceResult.createFailServiceResult("邮箱不能为空");
            }
            if(CommonUtils.isNUllOrEmpty(loginRequest.getPassword())){
                return ServiceResult.createFailServiceResult("密码不能为空");
            }


            List<UserDomain> userDomainList=  userService.queryUser(new UserDomain(null,loginRequest.getEmail(),null,null,null,null));
            if(CommonUtils.isNUllOrEmpty(userDomainList)){
                return ServiceResult.createFailServiceResult("登陆失败,请检测邮箱与密码");
            }
            if(userDomainList.size()>1){
                throw new RuntimeException("查找到两个相同账号的邮箱");
            }
            UserDomain userDomain = userDomainList.get(0);
            loginRequest.setPassword(generatePassword(loginRequest.getPassword(),userDomain.getPasswordSalt()));

            UserDomain ud = userService.login(loginRequest);
            if(CommonUtils.isNUll(ud)){
                return ServiceResult.createFailServiceResult("登陆失败,请检测邮箱与密码");
            }
            return ServiceResult.createSuccessServiceResult(new LoginResponse(ud.getUserId(),ud.getUserName()));
        } catch (Exception e) {
            String message = "用户登陆失败";
            LOGGER.error(message,e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
    }
}
