package org.prgrms.myboard.repository;

import org.prgrms.myboard.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
