package jehs.springbootboardjpa.repository;

import jehs.springbootboardjpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
