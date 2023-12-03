package com.example.board.domain.email.repository;

import com.example.board.domain.email.entity.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {

    boolean existsByEmailAndPurpose(String email, String purpose);

    Optional<EmailAuth> findByEmailAndPurpose(String email, String purpose);

    void deleteByEmail(String email);
}
