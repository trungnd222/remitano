package com.remitano.demo.util;

import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil {
    public static void writeCookie(String name, String value, HttpServletResponse resp) {
        ResponseCookie.ResponseCookieBuilder cookieBuilder = ResponseCookie.from(name, value)
                .path("/")
                .httpOnly(Boolean.TRUE)
                .secure(Boolean.TRUE)
                .sameSite("None")
                .maxAge(10 * 60 * 1000);
        String strCookie = cookieBuilder.build().toString();
        resp.addHeader("Set-Cookie", strCookie);
    }

    public static String getCookie(String name, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            return null;
        }
        for (Cookie co : cookies) {
            if (name.equals(co.getName())) {
                return co.getValue();
            }
        }
        return null;
    }

}
