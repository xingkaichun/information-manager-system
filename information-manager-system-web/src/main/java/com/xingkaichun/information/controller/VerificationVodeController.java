package com.xingkaichun.information.controller;

import com.alibaba.fastjson.JSON;
import com.xingkaichun.common.dto.base.FreshServiceResult;
import com.xingkaichun.information.dto.verificationVode.RandomAddVerificationCodeResponse;
import com.xingkaichun.information.service.VerificationCodeService;
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

    //region 邀请码
    @ApiOperation(value="产生邀请码", notes="产生邀请码")
    @ResponseBody
    @PostMapping("/RandomAddVerificationVode")
    public FreshServiceResult randomAddVerificationVode(HttpServletRequest req, HttpServletResponse res, @RequestBody com.xingkaichun.information.dto.verificationCode.RandomAddVerificationCodeRequest request){
        try {
            verificationCodeService.randomAddVerificationCode(request);
            return FreshServiceResult.createSuccessFreshServiceResult("产生邀请码成功");
        } catch (Exception e) {
            String message = "产生邀请码出错";
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
