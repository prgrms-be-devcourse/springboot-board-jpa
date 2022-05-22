package com.prgrms.springbootboardjpa.user.repository;

import com.prgrms.springbootboardjpa.user.entity.Email;
import com.prgrms.springbootboardjpa.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(Email email);
    User findByNickName(String nickname);
}
