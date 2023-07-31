package com.programmers.springbootboardjpa.repository;

import com.programmers.springbootboardjpa.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAllByOrderByNameAsc(Pageable pageable);

}
