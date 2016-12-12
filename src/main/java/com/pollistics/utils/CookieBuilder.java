package com.pollistics.utils;

import java.text.MessageFormat;
import javax.servlet.http.Cookie;

public abstract class CookieBuilder {
	public final static int COOKIE_EXPIRY_TIME = Integer.MAX_VALUE;
	public final static String COOKIE_PATH = "/";
	public final static String COOKIE_VOTED_NAME = "pollistics-voted";
	
	public static Cookie getVotedCookie(String oldValue, String newValue) {
		Cookie cookie;
		if (oldValue.equals("")) {
			cookie = new Cookie(COOKIE_VOTED_NAME, newValue);
		} else {
			cookie = new Cookie(COOKIE_VOTED_NAME, MessageFormat.format("{0}-{1}", newValue, oldValue));
		}
		cookie.setMaxAge(COOKIE_EXPIRY_TIME);
		cookie.setPath(COOKIE_PATH);
		return cookie;
	}
}
