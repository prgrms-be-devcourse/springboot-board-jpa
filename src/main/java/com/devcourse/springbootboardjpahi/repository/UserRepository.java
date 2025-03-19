package com.devcourse.springbootboardjpahi.repository;

import com.devcourse.springbootboardjpahi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
