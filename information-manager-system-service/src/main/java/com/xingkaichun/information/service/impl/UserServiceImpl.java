package com.xingkaichun.information.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingkaichun.information.dao.UserDao;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.UserInfo;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsMd5;
import com.xingkaichun.utils.CommonUtilsSession;
import org.apache.commons.lang3.CharEncoding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public ServiceResult<UserInfo> addUser(UserDto userDto) {
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
        //校验用户是否已经存在
        if(!CommonUtils.isNUll(userDao.queryUserByEmail(userDto.getEmail()))){
            return FreshServiceResult.createFailFreshServiceResult("邮箱已经存在");
        }
        if(!CommonUtils.isNUll(userDao.queryUserByUserName(userDto.getUserName()))){
            return FreshServiceResult.createFailFreshServiceResult("用户名已经存在");
        }
        if(!CommonUtils.isNUll(userDao.queryUserByPhone(userDto.getPhone()))){
            return FreshServiceResult.createFailFreshServiceResult("手机号已经存在");
        }

        userDto.setUserId(String.valueOf(UUID.randomUUID()));
        userDto.setPasswordSalt(String.valueOf(UUID.randomUUID()));
        String newPassword = generatePassword(userDto.getPassword(), userDto.getPasswordSalt());
        userDto.setPassword(newPassword);
        UserDomain userDomain = classClass(userDto);

        userDao.insert(userDomain);

        UserDomain userDomain2 = userDao.queryUserByUserId(userDomain.getUserId());

        return ServiceResult.createSuccessServiceResult("新增用戶成功",classCast(userDomain2));
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

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public PageInfo<UserDomain> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserDomain> userDomains = userDao.selectUsers();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }

    @Override
    public UserDomain queryOneUserByUserId(String userId) {
        return userDao.queryUserByUserId(userId);
    }
    @Override
    public UserInfo queryOneUserByUserId2(String userId) {
        UserDomain userDomain = userDao.queryUserByUserId(userId);
        return classCast(userDomain);
    }
    @Override
    public ServiceResult<UserInfo> queryOneUserByEmail(String email) {
        UserDomain userDomain = userDao.queryUserByEmail(email);
        if(userDomain == null){
            return ServiceResult.createFailServiceResult("通过邮箱和密码获取用户失败，请检查邮箱和密码");
        }
        UserInfo userInfo = classCast(userDomain);
        return ServiceResult.createSuccessServiceResult("通过邮箱获取用户成功",userInfo);
    }

    @Override
    public UserDomain queryOneUserByUserToken(String userToken) {
        return userDao.queryOneUserByUserToken(userToken);
    }

    @Override
    public ServiceResult<UserInfo> queryUserByEmailAndPassword(String email,String password) {
        UserDomain userDomain = userDao.queryUserByEmailAndPassword(email,password);
        if(userDomain == null){
            return ServiceResult.createFailServiceResult("通过邮箱和密码获取用户失败，请检查邮箱和密码");
        }
        UserInfo userInfo = classCast(userDomain);
        return ServiceResult.createSuccessServiceResult("通过邮箱和密码获取用户成功",userInfo);
    }

    @Override
    public int updateUserToken(UserDomain userDomain) {
        if(CommonUtils.isNUll(userDomain)){
            throw new NullPointerException("User不能为空");
        }
        if(CommonUtils.isNUllOrEmpty(userDomain.getUserId())){
            throw new NullPointerException("UserId不能为空");
        }
        if(CommonUtils.isNUllOrEmpty(userDomain.getUserToken())){
            throw new NullPointerException("UserToken不能为空");
        }
        return userDao.updateUserToken(userDomain);
    }

    @Override
    public UserInfo getUserInfoBySession(HttpServletRequest request) {
        UserDomain userDomain = CommonUtilsSession.getUser(request);
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

    private String generatePassword(String password, String passwordSalt) {
        return CommonUtilsMd5.MD5Encode(password+passwordSalt, CharEncoding.UTF_8,false);
    }

    private String generateUserToken() {
        return CommonUtilsMd5.MD5Encode(String.valueOf(UUID.randomUUID())+String.valueOf(UUID.randomUUID()), CharEncoding.UTF_8,false);
    }
}
