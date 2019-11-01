package com.xingkaichun.information.dto.verificationCode;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RandomAddVerificationCodeRequest {

    @JsonProperty("Number")
    private int number;
}
