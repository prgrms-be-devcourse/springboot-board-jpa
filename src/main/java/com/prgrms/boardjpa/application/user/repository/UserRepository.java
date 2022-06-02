package com.prgrms.boardjpa.application.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.boardjpa.application.user.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
