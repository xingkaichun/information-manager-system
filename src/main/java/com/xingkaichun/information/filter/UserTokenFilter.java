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

		boolean isSkip = false;
		if(uri.equals("/User/Login")){
			isSkip = true;
		}
		if(uri.toString().contains("/Error/")){
			isSkip = true;
		}
		if(uri.toString().contains(".html")){
			isSkip = true;
		}
		if(uri.toString().contains("jpg")){
			isSkip = true;
		}
		if(uri.toString().contains(".js")){
			isSkip = true;
		}
		if(uri.toString().contains(".css")){
			isSkip = true;
		}
        if(uri.toString().contains("/swagger-ui.html")){
            isSkip = true;
        }
        if(uri.toString().contains("/webjars/springfox-swagger-ui/")){
            isSkip = true;
        }
        if(uri.toString().contains("/swagger-resources")){
            isSkip = true;
        }
        if(uri.toString().contains("/api-docs")){
            isSkip = true;
        }
        if(uri.toString().contains("/favicon.ico")){
            isSkip = true;
        }
		if(!isSkip){
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
		System.out.println("User Token Filter OUT");
	}

	@Override
	public void destroy() {
		
	}
}