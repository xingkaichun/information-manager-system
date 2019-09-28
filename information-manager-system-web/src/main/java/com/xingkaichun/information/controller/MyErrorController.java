package com.xingkaichun.information.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
class MyErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401 || statusCode == 404 || statusCode == 403){
            return "error";
        }else{
            return "error";
        }
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}