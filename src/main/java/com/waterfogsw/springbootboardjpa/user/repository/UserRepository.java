package com.waterfogsw.springbootboardjpa.user.repository;

import com.waterfogsw.springbootboardjpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
