package com.xingkaichun.information.controller;

import com.xingkaichun.common.dto.base.FreshServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/Error")
public class ErrotController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrotController.class);

    @ResponseBody
    @RequestMapping(value = "Auth",method={RequestMethod.GET,RequestMethod.POST})
    public FreshServiceResult addArticle(){
        return FreshServiceResult.createFailFreshServiceResult("未登录，无权访问");
    }

}
