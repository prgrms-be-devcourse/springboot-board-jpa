package com.programmers.springboard.repository;

import com.programmers.springboard.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Groups, Long> {

}
