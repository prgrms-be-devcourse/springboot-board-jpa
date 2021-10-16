package com.board.springbootboard.domain.user;

import com.board.springbootboard.domain.posts.PostsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity,Long> {


    @Query("SELECT u FROM UserEntity u ORDER BY u.id DESC")
    List<PostsEntity> findAllDesc();

    

}
