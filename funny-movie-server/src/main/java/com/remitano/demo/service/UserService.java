package com.remitano.demo.service;

import com.remitano.demo.common.AppConstant;
import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.repository.UserRepository;
import com.remitano.demo.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity register(UserEntity userEntity) throws Exception {
        Optional<UserEntity> optionalUser = userRepository.findById(userEntity.getEmail());
        if (optionalUser.isPresent()) {
            throw new Exception(String.format("email: %s is exits!", userEntity.getEmail()));
        }
        String rawPassword = userEntity.getPassword();
        String securePassword = PasswordUtils.generateSecurePassword(rawPassword, AppConstant.SALT_PASSWORD);
        userEntity.setPassword(securePassword);
        return userRepository.saveAndFlush(userEntity);
    }

    public UserEntity login(String email, String pass) {
        Optional<UserEntity> optionalUser = userRepository.findById(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            boolean isOk = PasswordUtils.verifyUserPassword(pass, user.getPassword(), AppConstant.SALT_PASSWORD);
            if (isOk) {
                return user;
            }
        }
        return null;
    }

    public UserEntity updateSession(UserEntity user, String session) {
        List<String> sessions = user.getSessions();
        if (sessions == null) {
            sessions = new ArrayList<>();
        }
        sessions.add(session);
        user.setSessions(sessions);
        return userRepository.saveAndFlush(user);
    }

}
