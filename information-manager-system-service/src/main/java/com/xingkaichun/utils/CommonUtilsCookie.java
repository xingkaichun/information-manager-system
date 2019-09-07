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


    public static void saveUser(HttpServletResponse response, UserDomain userDomain) {
        String userToken = userDomain.getUserToken();
        Cookie userTokenCookie = new Cookie(USER_TOKEN,userToken);
        response.addCookie(userTokenCookie);
    }

    public static String getUserToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(USER_TOKEN.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
