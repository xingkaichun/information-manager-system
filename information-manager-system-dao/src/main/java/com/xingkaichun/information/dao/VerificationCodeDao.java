package com.xingkaichun.information.dao;

import com.xingkaichun.information.model.VerificationCodeDomain;
import org.apache.ibatis.annotations.Param;

public interface VerificationCodeDao {
    int insert(VerificationCodeDomain domain);
    VerificationCodeDomain queryByVerificationCode(@Param("verificationCode") String verificationCode);
    int update(VerificationCodeDomain domain);
}
