package com.programmers.epicblues.jpa_board.repository;

import com.programmers.epicblues.jpa_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, Long> {

}
