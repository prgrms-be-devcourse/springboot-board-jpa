package com.prgrms.be.app.repository;

import com.prgrms.be.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
