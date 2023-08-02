package dev.jpaboard.user.repository;

import dev.jpaboard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmailAndPassword(String email, String password);

}
