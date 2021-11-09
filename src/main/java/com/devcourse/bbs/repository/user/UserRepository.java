package com.devcourse.bbs.repository.user;

import com.devcourse.bbs.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);
    void deleteByName(String name);
    boolean existsByName(String name);
}