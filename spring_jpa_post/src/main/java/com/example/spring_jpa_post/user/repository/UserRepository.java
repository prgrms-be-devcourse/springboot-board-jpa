package com.example.spring_jpa_post.user.repository;

import com.example.spring_jpa_post.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
