package com.xingkaichun.information.service.impl;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dao.UserDao;
import com.xingkaichun.information.dao.VerificationCodeDao;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.dto.user.response.LoginResponse;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.model.VerificationCodeDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsCookie;
import com.xingkaichun.utils.CommonUtilsMd5;
import com.xingkaichun.utils.CommonUtilsSession;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private VerificationCodeDao verificationCodeDao;

    @Override
    public ServiceResult<UserInfo> addUser(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UserDto userDto) {
        if(CommonUtils.isNUllOrEmpty(userDto.getVerificationCode())){
            return FreshServiceResult.createFailFreshServiceResult("邀请码不能为空");
        }
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

        String verificationCode = userDto.getVerificationCode();
        if(CommonUtils.isNUllOrEmpty(verificationCode)){
            return FreshServiceResult.createFailFreshServiceResult("邀请码不能为空");
        }
        //校验邀请码是否已经使用
        VerificationCodeDomain verificationCodeDomain = verificationCodeDao.queryByVerificationCode(verificationCode);
        if(verificationCodeDomain==null){
            return FreshServiceResult.createFailFreshServiceResult("请输入正确的邀请码");
        }
        if(verificationCodeDomain.isUsed()){
            return FreshServiceResult.createFailFreshServiceResult("邀请码已经被使用过了，请输入一个未使用的邀请码");
        }

        //校验用户是否已经存在
        if(!CommonUtils.isNUll(userDao.queryUserByEmail(userDto.getEmail()))){
            return FreshServiceResult.createFailFreshServiceResult("邮箱已经存在");
        }
        if(!CommonUtils.isNUll(userDao.queryUserByUserName(userDto.getUserName()))){
            return FreshServiceResult.createFailFreshServiceResult("用户名已经存在");
        }

        userDto.setUserId(String.valueOf(UUID.randomUUID()));
        userDto.setPasswordSalt(String.valueOf(UUID.randomUUID()));
        String encryptionPassword = encryptionPassword(userDto.getPassword(), userDto.getPasswordSalt());
        userDto.setPassword(encryptionPassword);

        UserDomain userDomain = classClass(userDto);
        userDao.insert(userDomain);


        //销毁邀请码
        verificationCodeDomain.setUsed(true);
        verificationCodeDao.update(verificationCodeDomain);


        UserDomain userDomainByUserId = userDao.queryUserByUserId(userDomain.getUserId());
        cookieSession(httpServletRequest,httpServletResponse,userDomainByUserId);
        return ServiceResult.createSuccessServiceResult("新增用戶成功",classCast(userDomainByUserId));
    }

    private void cookieSession(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UserDomain userDomain) {
        CommonUtilsSession.saveUser(httpServletRequest,userDomain);
        String userToken = generateUserToken();
        userDomain.setUserToken(userToken);
        userDao.updateUserToken(userDomain);
        CommonUtilsCookie.saveUserToken(httpServletResponse,userToken);
    }

    private UserDomain classClass(UserDto userDto) {
        UserDomain userDomain = new UserDomain();
        userDomain.setUserId(userDto.getUserId());
        userDomain.setEmail(userDto.getEmail());
        userDomain.setUserName(userDto.getUserName());
        userDomain.setPassword(userDto.getPassword());
        userDomain.setPasswordSalt(userDto.getPasswordSalt());
        userDomain.setPhone(userDto.getPhone());
        return userDomain;
    }

    @Override
    public UserInfo getUserInfoBySession(HttpServletRequest request) {
        UserDomain userDomain = CommonUtilsSession.getUser(request);
        return classCast(userDomain);
    }

    @Override
    public void updateUserSession(ServletRequest httpServletRequest) {
        String userToken = CommonUtilsCookie.getUserToken((HttpServletRequest) httpServletRequest);
        if(CommonUtils.isNUllOrEmpty(userToken)){
            return;
        }

        UserDomain userDomain = userDao.queryOneUserByUserToken(userToken);
        if(!CommonUtils.isNUll(userDomain)){
            CommonUtilsSession.saveUser((HttpServletRequest) httpServletRequest,userDomain);
        }
    }

    @Override
    public ServiceResult<LoginResponse> login(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, LoginRequest loginRequest) {
        if(CommonUtils.isNUllOrEmpty(loginRequest.getEmail())){
            return ServiceResult.createFailServiceResult("邮箱不能为空");
        }
        if(CommonUtils.isNUllOrEmpty(loginRequest.getPassword())){
            return ServiceResult.createFailServiceResult("密码不能为空");
        }

        //检测邮箱是否存在
        UserDomain userByEmail = userDao.queryUserByEmail(loginRequest.getEmail());
        if(userByEmail == null){
            return ServiceResult.createFailServiceResult("登陆失败,请检测邮箱与密码");
        }
        //比较密码是否正确
        UserInfo userInfo = classCast(userByEmail);
        UserDomain userDomain = userDao.queryUserByUserId(userInfo.getUserId());
        loginRequest.setPassword(encryptionPassword(loginRequest.getPassword(),userDomain.getPasswordSalt()));
        UserDomain userByEmailAndPassword = userDao.queryUserByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPassword());
        if(userByEmailAndPassword == null){
            return ServiceResult.createFailServiceResult("登陆失败,请检测邮箱与密码");
        }


        cookieSession(httpServletRequest,httpServletResponse,userDomain);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserInfo(userInfo);
        return ServiceResult.createSuccessServiceResult("用户登陆成功",loginResponse);
    }

    @Override
    public UserInfo queryUserByUserId(String userId) {
        UserDomain userDomain = userDao.queryUserByUserId(userId);
        return classCast(userDomain);
    }

    private UserInfo classCast(UserDomain userDomain) {
        if(userDomain == null){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userDomain.getUserId());
        userInfo.setUserName(userDomain.getUserName());
        return userInfo;
    }

    private String encryptionPassword(String password, String passwordSalt) {
        return CommonUtilsMd5.MD5Encode(password+passwordSalt, CharEncoding.UTF_8,false);
    }

    private String generateUserToken() {
        return CommonUtilsMd5.MD5Encode(String.valueOf(UUID.randomUUID())+String.valueOf(UUID.randomUUID()), CharEncoding.UTF_8,false);
    }
}
