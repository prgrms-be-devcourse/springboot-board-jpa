package com.maenguin.kdtbbs.repository;

import com.maenguin.kdtbbs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
