package com.programmers.epicblues.board.repository;

import com.programmers.epicblues.board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

}
