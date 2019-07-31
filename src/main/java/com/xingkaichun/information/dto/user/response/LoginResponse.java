package com.xingkaichun.information.dto.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xingkaichun.information.dto.user.UserInfo;
import lombok.Data;

@Data
public class LoginResponse {

    @JsonProperty("UserInfo")
    private UserInfo userInfo;

    public LoginResponse() {
    }

    public LoginResponse(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
