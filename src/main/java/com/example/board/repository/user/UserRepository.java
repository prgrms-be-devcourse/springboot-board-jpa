package com.example.board.repository.user;

import com.example.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByIdAndDeletedAt(Long id, LocalDateTime deletedAt);

    List<User> findByNameAndDeletedAt(String name, LocalDateTime deletedAt);
}
