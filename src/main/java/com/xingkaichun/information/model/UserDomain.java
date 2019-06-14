package com.xingkaichun.information.model;

public class UserDomain {

    private String userId;

    private String email;

    private String userName;

    private String password;

    private String passwordSalt;

    private String phone;

    private String userToken;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}