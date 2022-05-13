package com.remitano.demo.repository;

import com.remitano.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
}
