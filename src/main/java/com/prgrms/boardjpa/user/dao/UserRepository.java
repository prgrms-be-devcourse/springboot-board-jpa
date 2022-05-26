package com.prgrms.boardjpa.user.dao;

import com.prgrms.boardjpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
