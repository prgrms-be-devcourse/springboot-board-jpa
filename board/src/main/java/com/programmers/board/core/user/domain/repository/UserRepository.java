package com.programmers.board.core.user.domain.repository;

import com.programmers.board.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
}
