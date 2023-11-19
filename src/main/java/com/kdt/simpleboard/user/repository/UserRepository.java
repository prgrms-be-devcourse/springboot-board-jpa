package com.kdt.simpleboard.user.repository;

import com.kdt.simpleboard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
