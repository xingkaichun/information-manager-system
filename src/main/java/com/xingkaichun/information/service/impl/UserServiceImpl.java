package com.xingkaichun.information.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingkaichun.information.dao.UserDao;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.information.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public int addUser(UserDto userDto) {
        UserDomain userDomain = classClass(userDto);
        return userDao.insert(userDomain);
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
    public UserDomain queryOneUserByEmail(String email) {
        return userDao.queryUserByEmail(email);
    }

    @Override
    public UserDomain queryOneUserByUserName(String userName) {
        return userDao.queryUserByUserName(userName);
    }

    @Override
    public UserDomain queryOneUserByPhone(String phone) {
        return userDao.queryUserByPhone(phone);
    }

    @Override
    public UserDomain queryOneUserByUserToken(String userToken) {
        return userDao.queryOneUserByUserToken(userToken);
    }

    @Override
    public UserDomain queryUserByEmailAndPassword(String email,String password) {
        UserDomain userDomain = userDao.queryUserByEmailAndPassword(email,password);
        return userDomain;
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
}
