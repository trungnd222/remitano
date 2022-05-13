package com.remitano.demo.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.remitano.demo.dto.RegisterForm;
import com.remitano.demo.dto.UserDTO;
import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private Mapper mapper;

    public UserEntity convert2Entity(RegisterForm form) {
        return mapper.map(form, UserEntity.class);
    }

    public UserDTO convert2DTO(UserEntity userEntity) {
        return mapper.map(userEntity, UserDTO.class);
    }
}
