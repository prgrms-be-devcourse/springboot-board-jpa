package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
}
