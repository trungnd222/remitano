package com.remitano.demo.service;

import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.repository.UserRepository;
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
        return userRepository.saveAndFlush(userEntity);
    }

    public UserEntity login(String email, String pass) {
        Optional<UserEntity> optionalUser = userRepository.findById(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (user.getPassword().equals(pass)) {
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
