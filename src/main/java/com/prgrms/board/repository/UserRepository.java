package com.prgrms.board.repository;

import com.prgrms.board.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    @Query("SELECT COUNT(u) > 0 FROM Users u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    Optional<Users> findByEmail(String email);
}
