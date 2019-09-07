package com.xingkaichun.information.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description="用户的实体对象")
public class UserDto {

    @JsonProperty("UserId")
    private String userId;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("Password")
    private String password;
    @JsonProperty("PasswordSalt")
    private String passwordSalt;
    @JsonProperty("Phone")
    private String phone;
}