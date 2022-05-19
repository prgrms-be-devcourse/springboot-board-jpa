package com.prgrms.boardapp.repository;

import com.prgrms.boardapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
