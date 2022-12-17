package com.devcourse.springbootboardjpa.post.repository;

import com.devcourse.springbootboardjpa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
