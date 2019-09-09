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
import javax.servlet.http.HttpServletResponse;

import com.xingkaichun.information.model.UserDomain;
import com.xingkaichun.information.service.UserService;
import com.xingkaichun.utils.CommonUtils;
import com.xingkaichun.utils.CommonUtilsCookie;
import com.xingkaichun.utils.CommonUtilsSession;
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

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String uri = httpServletRequest.getRequestURI();

		//是否需要权限
		boolean needAccess = false;

		//后台强制验证所有操作都需要权限
		needAccess = true;

		if (uri.contains("/Add")||uri.contains("/Delete")||uri.contains("/Update")){
			needAccess = true ;
		}

		if (uri.contains("/User/Login")||uri.contains("/Error/Auth")){
			needAccess = false ;
		}

		if(needAccess){
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
			if(CommonUtils.isNUll(CommonUtilsSession.getUser(httpServletRequest))){
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				httpServletResponse.sendRedirect("/Error/Auth");
				return;
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}
}