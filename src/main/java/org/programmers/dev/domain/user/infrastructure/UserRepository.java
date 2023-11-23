package org.programmers.dev.domain.user.infrastructure;

import org.programmers.dev.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
