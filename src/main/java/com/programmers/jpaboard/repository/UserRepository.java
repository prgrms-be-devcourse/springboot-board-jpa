package com.programmers.jpaboard.repository;

import com.programmers.jpaboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
