package com.xingkaichun.information.model;

import lombok.Data;

@Data
public class UserDomain {

    private String userId;
    private String email;
    private String userName;
    private String password;
    private String passwordSalt;
    private String phone;
    private String userToken;
    private Integer forbid;


    public UserDomain() {
    }

    public UserDomain(String userId, String email, String userName, String password, String passwordSalt, String phone) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.passwordSalt = passwordSalt;
        this.phone = phone;
    }
}