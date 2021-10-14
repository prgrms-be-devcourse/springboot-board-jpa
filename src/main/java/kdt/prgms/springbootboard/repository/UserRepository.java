package kdt.prgms.springbootboard.repository;


import java.util.Optional;
import kdt.prgms.springbootboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);
}
