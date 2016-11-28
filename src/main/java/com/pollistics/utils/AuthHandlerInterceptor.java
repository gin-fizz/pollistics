package com.pollistics.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pollistics.models.SecUserDetails;

public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(final HttpServletRequest request,
						   final HttpServletResponse response, final Object handler,
						   final ModelAndView modelAndView) throws Exception {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object user = auth.getPrincipal();
			if (modelAndView != null && user instanceof SecUserDetails) {
				modelAndView.getModelMap().addAttribute("user", ((SecUserDetails) user).getUser());
			}
		}		
	}
}

