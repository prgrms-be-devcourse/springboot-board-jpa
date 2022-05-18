package org.programmers.springbootboardjpa.repository;

import org.programmers.springbootboardjpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}