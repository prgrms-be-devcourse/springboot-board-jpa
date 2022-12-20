package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.domian.Post;
import com.example.springbootboardjpa.domian.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserName(String user);

    @Query("select p from Post as p where p.title like %:title%")
    List<Post> findByTitle(@Param("title") String title);
}
