package com.will.jpapractice.domain.user.repository;

import com.will.jpapractice.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
