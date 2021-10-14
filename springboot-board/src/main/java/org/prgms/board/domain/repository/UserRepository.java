package org.prgms.board.domain.repository;

import org.prgms.board.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndDeleted(Long id, boolean deleted);
}
