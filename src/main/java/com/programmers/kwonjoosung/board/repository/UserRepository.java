package com.programmers.kwonjoosung.board.repository;

import com.programmers.kwonjoosung.board.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
