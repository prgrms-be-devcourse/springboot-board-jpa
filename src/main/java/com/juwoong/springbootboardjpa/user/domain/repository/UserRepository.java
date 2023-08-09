package com.juwoong.springbootboardjpa.user.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.juwoong.springbootboardjpa.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    //@EntityGraph(attributePaths = "posts")
    Optional<User> findById(Long id);

}
