package com.xingkaichun.information.dao;


import com.xingkaichun.information.model.UserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    int insert(UserDomain userDomain);
    List<UserDomain> selectUsers();

    UserDomain queryUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

    UserDomain queryUserByUserId(String userId);

    UserDomain queryUserByEmail(String email);

    UserDomain queryUserByUserName(String userName);

    UserDomain queryUserByPhone(String phone);

    int updateUserToken(UserDomain userDomain);

    UserDomain queryOneUserByUserToken(String userToken);
}