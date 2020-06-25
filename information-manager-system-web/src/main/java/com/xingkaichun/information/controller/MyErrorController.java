package com.xingkaichun.information.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
class MyErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request,Exception e){
        logger.error("全局异常处理捕获到异常。",e);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("timestamp",System.currentTimeMillis());
        modelAndView.addObject("error","StackTrace");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        modelAndView.addObject("status",statusCode);
        modelAndView.addObject("message","message");
        if(statusCode == 401 || statusCode == 404 || statusCode == 403){
        }else{
        }
        //TODO 记录异常
        return modelAndView;
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}