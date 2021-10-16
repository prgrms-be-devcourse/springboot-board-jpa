package com.kdt.bulletinboard.repository;

import com.kdt.bulletinboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
