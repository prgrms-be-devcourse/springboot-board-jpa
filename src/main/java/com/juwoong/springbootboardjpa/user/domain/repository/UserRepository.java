package com.juwoong.springbootboardjpa.user.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.juwoong.springbootboardjpa.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
