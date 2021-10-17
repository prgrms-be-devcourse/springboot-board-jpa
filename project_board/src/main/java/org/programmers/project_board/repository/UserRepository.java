package org.programmers.project_board.repository;

import org.programmers.project_board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
