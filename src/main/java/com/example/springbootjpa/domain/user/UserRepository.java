package com.example.springbootjpa.domain.user;

import com.example.springbootjpa.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
