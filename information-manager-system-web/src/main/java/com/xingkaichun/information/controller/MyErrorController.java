package com.xingkaichun.information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Controller
class MyErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request,Exception e){
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