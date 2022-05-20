package com.prgrms.board.user.repository;

import com.prgrms.board.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Slice<User> findSliceBy(Pageable pageable);

}
