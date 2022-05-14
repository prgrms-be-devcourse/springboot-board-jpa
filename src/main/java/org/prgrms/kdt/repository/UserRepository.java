package org.prgrms.kdt.repository;

import org.prgrms.kdt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}