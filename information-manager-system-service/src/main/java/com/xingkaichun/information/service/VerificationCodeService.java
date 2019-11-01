package com.xingkaichun.information.service;

import com.xingkaichun.information.dto.verificationCode.RandomAddVerificationCodeRequest;

public interface VerificationCodeService {
    void randomAddVerificationCode(RandomAddVerificationCodeRequest request);
}
