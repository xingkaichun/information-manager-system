package com.xingkaichun.utils;

import com.xingkaichun.information.model.UserDomain;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 邢开春
 */
public class CommonUtilsCookie {

    private final static String USER_TOKEN = "UserToken" ;


    public static void saveUserToken(HttpServletResponse response, String userToken) {
        Cookie userTokenCookie = new Cookie(USER_TOKEN,userToken);
        userTokenCookie.setPath("/");
        response.addCookie(userTokenCookie);
    }

    public static String getUserToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null){return null;}
        for(Cookie cookie:cookies){
            if(USER_TOKEN.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
