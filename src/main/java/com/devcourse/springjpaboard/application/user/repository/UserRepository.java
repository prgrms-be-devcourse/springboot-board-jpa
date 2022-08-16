package com.devcourse.springjpaboard.application.user.repository;

import com.devcourse.springjpaboard.application.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
