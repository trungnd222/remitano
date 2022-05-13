package com.remitano.demo.filter;


import com.remitano.demo.common.AppConstant;
import com.remitano.demo.repository.UserRepository;
import com.remitano.demo.security.UserDetailSecurity;
import com.remitano.demo.security.UserDetailServiceSecurity;
import com.remitano.demo.util.HttpUtil;
import com.remitano.demo.util.TokenUtil;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Order(1)
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LogManager.getLogger(AuthenticationFilter.class);
    @Autowired
    private UserDetailServiceSecurity userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void doFilterInternal(@NonNull HttpServletRequest req,
                                 @NonNull HttpServletResponse resp,
                                 @NonNull FilterChain chain) throws IOException, ServletException {
        try {
            String session = HttpUtil.getCookie(AppConstant.COOKIE_NAME, req);
            Claims claims = TokenUtil.parse(session);
            String email = claims.getId();
            UserDetailSecurity userDetailSecurity = userService.loadUserByEmail(email);
            if (!userDetailSecurity.isEnabled()) {
                throw new Exception(String.format("User is disable | email: %d", email));
            }
            List<String> lstSession = userDetailSecurity.getUser().getSessions();
            if (lstSession.contains(session)) {
                UsernamePasswordAuthenticationToken auth
                        = new UsernamePasswordAuthenticationToken(userDetailSecurity,
                        userDetailSecurity.getPassword(), userDetailSecurity.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.clearContext();
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }   catch (Exception ex) {
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
            resp.setHeader("Content-Type", "application/json");
        }
        chain.doFilter(req, resp);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return false;
    }

}
