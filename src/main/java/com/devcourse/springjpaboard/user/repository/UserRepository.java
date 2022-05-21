package com.devcourse.springjpaboard.user.repository;

import com.devcourse.springjpaboard.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
