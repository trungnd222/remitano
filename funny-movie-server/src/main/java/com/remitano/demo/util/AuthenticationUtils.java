package com.remitano.demo.util;

import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.security.UserDetailSecurity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class AuthenticationUtils {
    private static final Logger LOGGER = LogManager.getLogger();
    public static UserEntity getAuthUser(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        if (authentication.getPrincipal() instanceof UserDetailSecurity) {
            UserDetailSecurity userDetails = (UserDetailSecurity) authentication.getPrincipal();
            return userDetails.getUser();
        }
        return null;
    }

    public static String randomUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }



}
