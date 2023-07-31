package com.kdt.devcourse.module.user.domain.repository;

import com.kdt.devcourse.module.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
