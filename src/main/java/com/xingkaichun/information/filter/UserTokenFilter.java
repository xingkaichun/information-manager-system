package com.xingkaichun.information.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.information.utils.CommonUtils;
import com.xingkaichun.information.utils.CommonUtilsCookie;
import com.xingkaichun.information.utils.CommonUtilsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

@Order(1)
@WebFilter(filterName="UserTokenFilter", urlPatterns="/*")
public class UserTokenFilter implements Filter {

	@Autowired
	private UserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("User Token Filter IN");
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String uri = httpServletRequest.getRequestURI();

		boolean isLogin = false;
		if(uri.equals("/User/Login")){
			isLogin = true;
		}
		boolean isAddUser = false;
		if(uri.equals("/User/AddUser")){
			isLogin = true;
		}
		if(!isLogin && !isAddUser){
			//检测用户是否已经登录
			if(CommonUtils.isNUll(CommonUtilsSession.getUser(httpServletRequest))){
				//尝试使用UserToken登录
				String userToken = CommonUtilsCookie.getUserToken(httpServletRequest);
				if(!CommonUtils.isNUllOrEmpty(userToken)){
					UserDomain userDomain = userService.queryOneUserByUserToken(userToken);
					if(!CommonUtils.isNUll(userDomain)){
						CommonUtilsSession.saveUser(httpServletRequest,userDomain);
					}
				}
			}
		}
		chain.doFilter(request, response);
		System.out.println("User Token Filter OUT");
	}

	@Override
	public void destroy() {
		
	}
}