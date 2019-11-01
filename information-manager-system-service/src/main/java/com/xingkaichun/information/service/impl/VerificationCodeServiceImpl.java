package com.xingkaichun.information.service.impl;

import com.xingkaichun.information.dao.VerificationCodeDao;
import com.xingkaichun.information.dto.verificationCode.RandomAddVerificationCodeRequest;
import com.xingkaichun.information.model.VerificationCodeDomain;
import com.xingkaichun.information.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "verificationCodeService")
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeDao verificationCodeDao;

    int insert(VerificationCodeDomain domain){
        return verificationCodeDao.insert(domain);
    }

    VerificationCodeDomain queryByVerificationCode(String verificationCode){
        return verificationCodeDao.queryByVerificationCode(verificationCode);
    }

    int update(VerificationCodeDomain domain){
        return verificationCodeDao.update(domain);
    }

    @Override
    public void randomAddVerificationCode(RandomAddVerificationCodeRequest request) {
        List<VerificationCodeDomain> list = new ArrayList<>();
        for(int i=0;i<request.getNumber();i++){
            VerificationCodeDomain domain = new VerificationCodeDomain();
            domain.setUsed(false);
            domain.setVerificationCode(UUID.randomUUID().toString());
            verificationCodeDao.insert(domain);
        }
    }
}
