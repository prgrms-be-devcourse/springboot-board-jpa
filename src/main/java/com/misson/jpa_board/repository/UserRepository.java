package com.misson.jpa_board.repository;

import com.misson.jpa_board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
