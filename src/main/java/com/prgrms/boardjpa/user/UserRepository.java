package com.prgrms.boardjpa.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prgrms.boardjpa.user.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
