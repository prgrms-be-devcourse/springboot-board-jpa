package com.programmers.jpa_board.user.infra;

import com.programmers.jpa_board.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
