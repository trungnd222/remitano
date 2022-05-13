package com.remitano.demo.controller;

import com.remitano.demo.common.AppConstant;
import com.remitano.demo.common.BaseResponse;
import com.remitano.demo.common.ErrorCode;
import com.remitano.demo.converter.UserConverter;
import com.remitano.demo.dto.LoginForm;
import com.remitano.demo.dto.MovieDTO;
import com.remitano.demo.dto.PageDTO;
import com.remitano.demo.dto.RegisterForm;
import com.remitano.demo.entity.MovieEntity;
import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.repository.UserRepository;
import com.remitano.demo.service.UserService;
import com.remitano.demo.util.AuthenticationUtils;
import com.remitano.demo.util.HttpUtil;
import com.remitano.demo.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @GetMapping(path = "/get")
    public ResponseEntity<BaseResponse> find(Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user == null) {
                throw new Exception("please login now!");
            }
            response.setData(userConverter.convert2DTO(user));
        }catch (Exception e) {
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterForm form, Authentication auth) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user != null) {
                throw new Exception("logged");
            }
            UserEntity userEntity = userConverter.convert2Entity(form);
            userService.register(userEntity);
            response.setError(ErrorCode.SUCCESS);
        }catch (Exception e) {
            response.setError(ErrorCode.FAILED);
            response.setMsg("email or password is invalid");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity<BaseResponse> login(@RequestBody LoginForm form, Authentication auth, HttpServletResponse resp) {
        BaseResponse response = new BaseResponse();
        try{
            UserEntity user = AuthenticationUtils.getAuthUser(auth);
            if (user != null) {
                throw new Exception("logged");
            }
            user = userService.login(form.getEmail(), form.getPassword());
            if (user == null) {
                throw new Exception("email or password is wrong!");
            }
            String session = TokenUtil.generate(user.getEmail(), 43200000L);
            userService.updateSession(user, session);
            HttpUtil.writeCookie(AppConstant.COOKIE_NAME, session, resp);
            response.setError(ErrorCode.SUCCESS);
            response.setData(userConverter.convert2DTO(user));
        }catch (Exception e) {
            e.printStackTrace();
            response.setError(ErrorCode.FAILED);
            response.setMsg(e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

}
