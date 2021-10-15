package org.jpa.kdtboard.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yunyun on 2021/10/11.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByName(String name);
}
