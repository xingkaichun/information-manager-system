package com.xingkaichun.information.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserInfo {

    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("UserName")
    private String userName;

    public UserInfo() {
    }

    public UserInfo(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
