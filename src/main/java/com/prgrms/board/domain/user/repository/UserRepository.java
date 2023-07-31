package com.prgrms.board.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.board.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
