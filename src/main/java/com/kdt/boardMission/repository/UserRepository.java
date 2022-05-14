package com.kdt.boardMission.repository;

import com.kdt.boardMission.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
