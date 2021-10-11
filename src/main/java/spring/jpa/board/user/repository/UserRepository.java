package spring.jpa.board.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.jpa.board.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
