package com.eunu.springbootboard.dao.user;

import com.eunu.springbootboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
