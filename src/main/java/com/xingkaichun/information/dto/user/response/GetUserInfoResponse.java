package com.xingkaichun.information.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.user.UserInfo;

public class GetUserInfoResponse {

    @JsonProperty("UserInfo")
    private UserInfo userInfo;

    public GetUserInfoResponse() {
    }

    public GetUserInfoResponse(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
