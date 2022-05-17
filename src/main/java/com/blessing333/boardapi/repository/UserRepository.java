package com.blessing333.boardapi.repository;

import com.blessing333.boardapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
