package com.remitano.demo.repository;

import com.remitano.demo.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
}
