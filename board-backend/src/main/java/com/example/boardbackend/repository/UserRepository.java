package com.example.boardbackend.repository;

import com.example.boardbackend.domain.User;
import com.example.boardbackend.domain.embeded.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(Email email);
}
