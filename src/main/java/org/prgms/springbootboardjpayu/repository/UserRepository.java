package org.prgms.springbootboardjpayu.repository;

import org.prgms.springbootboardjpayu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
