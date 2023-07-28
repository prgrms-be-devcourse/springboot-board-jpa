package com.jpaboard.domain.user.infrastructure;

import com.jpaboard.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
