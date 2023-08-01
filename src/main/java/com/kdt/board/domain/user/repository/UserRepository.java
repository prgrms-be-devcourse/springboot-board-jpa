package com.kdt.board.domain.user.repository;

import com.kdt.board.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}