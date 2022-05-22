package com.dojinyou.devcourse.boardjpa.user.repository;

import com.dojinyou.devcourse.boardjpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
