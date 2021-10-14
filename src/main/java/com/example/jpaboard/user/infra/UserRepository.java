package com.example.jpaboard.user.infra;

import com.example.jpaboard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
