package com.example.board.repository.user;

import com.example.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByNameAndDeletedAt(String name, LocalDateTime deletedAt);
}
