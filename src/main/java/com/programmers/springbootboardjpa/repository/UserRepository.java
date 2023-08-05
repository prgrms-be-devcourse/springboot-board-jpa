package com.programmers.springbootboardjpa.repository;

import com.programmers.springbootboardjpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
