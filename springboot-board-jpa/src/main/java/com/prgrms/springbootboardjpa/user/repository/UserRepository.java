package com.prgrms.springbootboardjpa.user.repository;

import com.prgrms.springbootboardjpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
