package com.prgrms.devcourse.springjpaboard.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.devcourse.springjpaboard.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
