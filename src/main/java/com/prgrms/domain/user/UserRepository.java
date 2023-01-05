package com.prgrms.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select m from User m where m.email = :email and m.password = :password")
    Optional<User> findUser(String email, String password);

    boolean existsByEmail(String email);
}
