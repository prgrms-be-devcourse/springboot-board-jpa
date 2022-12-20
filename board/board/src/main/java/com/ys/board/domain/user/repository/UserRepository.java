package com.ys.board.domain.user.repository;

import com.ys.board.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where u.name = ?1")
    boolean existsByName(String name);

}
