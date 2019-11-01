package com.xingkaichun.information.controller;

import com.alibaba.fastjson.JSON;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.common.dto.base.ServiceResult;
import com.xingkaichun.information.service.VerificationCodeService;
import com.xingkaichun.information.dto.verificationCode.RandomAddVerificationCodeResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/VerificationVode")
public class VerificationVodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VerificationVodeController.class);

    @Autowired
    private VerificationCodeService verificationCodeService;

    //region 验证码
    @ApiOperation(value="产生验证码", notes="产生验证码")
    @ResponseBody
    @PostMapping("/RandomAddVerificationVode")
    public ServiceResult<RandomAddVerificationCodeResponse> randomAddVerificationVode(HttpServletRequest req, HttpServletResponse res, @RequestBody com.xingkaichun.information.dto.verificationCode.RandomAddVerificationCodeRequest request){
        try {
            verificationCodeService.randomAddVerificationCode(request);
            RandomAddVerificationCodeResponse response = new RandomAddVerificationCodeResponse();
            return ServiceResult.createSuccessServiceResult("产生验证码成功",response);
        } catch (Exception e) {
            String message = "产生验证码出错";
            LOGGER.error(message + getRequestString(request),e);
            return FreshServiceResult.createFailFreshServiceResult(message);
        } finally {
        }
    }
    private String getRequestString(Object request) {
        if(request==null){
            return "";
        }
        return JSON.toJSONString(request);
    }
}
