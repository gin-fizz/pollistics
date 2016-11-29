package com.pollistics.utils;

import com.pollistics.models.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthHandlerInterceptor extends HandlerInterceptorAdapter {
	@Override
	public void postHandle(final HttpServletRequest request,
						   final HttpServletResponse response, final Object handler,
						   final ModelAndView modelAndView) throws Exception {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object user = auth.getPrincipal();
			if (modelAndView != null && user instanceof User) {
				modelAndView.getModelMap().addAttribute("user", (User) user);
			}
		}
	}
}

