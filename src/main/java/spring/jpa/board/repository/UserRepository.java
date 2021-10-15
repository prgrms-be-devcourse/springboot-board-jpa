package spring.jpa.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.jpa.board.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
