package com.remitano.demo.security;

import com.remitano.demo.entity.UserEntity;
import com.remitano.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserDetailServiceSecurity implements UserDetailsService {
    private static final String ROLE_USER_ID = "user";
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(email);
        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException(String.format("email: %s is not found!", email));
        }
        return buildUserDetails(optionalUser.get());
    }

    private UserDetailSecurity buildUserDetails(UserEntity user) {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(ROLE_USER_ID));
        return new UserDetailSecurity(user, authorityList);
    }

    public UserDetailSecurity loadUserByEmail(String email) throws Exception {
        Optional<UserEntity> user = userRepository.findById(email);
        return buildUserDetails(user.orElseThrow(() -> new Exception(String.format("email: %s is not found", user))));
    }

}
