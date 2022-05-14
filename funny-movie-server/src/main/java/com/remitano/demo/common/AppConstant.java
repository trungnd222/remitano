package com.remitano.demo.common;


import com.remitano.demo.util.PasswordUtils;

public class AppConstant {
    public static final String COOKIE_NAME = "remitano_ss";
    public static final String SALT_PASSWORD = PasswordUtils.getSalt(30);
}
