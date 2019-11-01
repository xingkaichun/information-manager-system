package com.xingkaichun.information.model;

import lombok.Data;

import java.util.Date;

@Data
public class VerificationCodeDomain {
    private String verificationCode;
    private boolean used;
    private String useType;
    private String useLogInfo;
    private Date useTime;
}
