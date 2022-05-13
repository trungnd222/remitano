package com.remitano.demo.config;

import com.remitano.demo.filter.AuthenticationFilter;
import com.remitano.demo.security.UserDetailServiceSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private AuthenticationFilter cookieAuthenticationFilter;

    @Autowired
    private UserDetailServiceSecurity userDetailServiceSecurity;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(cookieAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/api/movie/share").authenticated()
                .antMatchers("/api/user/**").permitAll()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
                .rememberMe().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceSecurity);
    }

}
