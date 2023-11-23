package kdt.jpa.board.user.repository;

import kdt.jpa.board.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
