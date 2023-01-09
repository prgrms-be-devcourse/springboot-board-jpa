package kdt.springbootboardjpa.respository;

import kdt.springbootboardjpa.respository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
