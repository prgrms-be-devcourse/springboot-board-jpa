package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.domian.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
}
