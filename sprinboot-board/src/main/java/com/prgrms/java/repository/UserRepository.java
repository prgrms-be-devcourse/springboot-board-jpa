package com.prgrms.java.repository;

import com.prgrms.java.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Long countUserByEmail(String email);

    Optional<User> findByEmail(String email);
}
