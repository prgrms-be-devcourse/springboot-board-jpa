package com.eden6187.jpaboard.repository;

import com.eden6187.jpaboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
