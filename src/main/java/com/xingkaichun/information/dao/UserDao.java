package com.xingkaichun.information.dao;


import com.xingkaichun.information.model.UserDomain;

import java.util.List;

public interface UserDao {

    int insert(UserDomain record);
    List<UserDomain> selectUsers();
}