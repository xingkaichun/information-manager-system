package com.xingkaichun.utils;

import com.xingkaichun.information.model.UserDomain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonUtilsSession {

    private final static String USER_NAME = "UserDomain" ;


    public static void saveUser(HttpServletRequest request, UserDomain userDomain) {
        HttpSession session = request.getSession();
        session.setAttribute(USER_NAME,userDomain);
    }

    public static UserDomain getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (UserDomain) session.getAttribute(USER_NAME);
    }

}
