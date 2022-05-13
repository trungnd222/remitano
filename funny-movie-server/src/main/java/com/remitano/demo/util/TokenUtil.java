package com.remitano.demo.util;

import io.jsonwebtoken.*;

import java.time.Instant;
import java.util.Date;


public class TokenUtil {

    private static final String ACCESS_TOKEN_KEY = "trungnd";
    public static final long MAX_AGE = 1000000000000L;
    public static final long DEFAULT_AGE = 43200000L;

    public static Claims parse(String token) {
        return parseToken(token, ACCESS_TOKEN_KEY);
    }

    public static String generate(String email, long expiredTime) {
        return Jwts.builder().setId(email)
                .setExpiration(Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + expiredTime)))
                .signWith(SignatureAlgorithm.HS256, ACCESS_TOKEN_KEY.getBytes()).compact();
    }

    private static Claims parseToken(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token).getBody();
    }






}
