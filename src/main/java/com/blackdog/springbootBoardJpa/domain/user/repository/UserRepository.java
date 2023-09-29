package com.blackdog.springbootBoardJpa.domain.user.repository;

import com.blackdog.springbootBoardJpa.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
