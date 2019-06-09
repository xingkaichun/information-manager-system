package com.xingkaichun.information.dao;


import com.xingkaichun.information.model.UserDomain;

import java.util.List;

public interface UserDao {

    int insert(UserDomain userDomain);
    List<UserDomain> selectUsers();

    List<UserDomain> queryUser(UserDomain userDomain);
}