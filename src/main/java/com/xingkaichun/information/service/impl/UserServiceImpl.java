package com.xingkaichun.information.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xingkaichun.information.dao.UserDao;
import com.xingkaichun.information.dto.user.UserDto;
import com.xingkaichun.information.dto.user.request.LoginRequest;
import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
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
    public List<UserDomain> queryUser(UserDomain userDomain) {
        return userDao.queryUser(userDomain);
    }

    @Override
    public UserDomain login(LoginRequest loginRequest) {
        UserDomain userDomain = userDao.login(loginRequest);
        return userDomain;
    }
}
