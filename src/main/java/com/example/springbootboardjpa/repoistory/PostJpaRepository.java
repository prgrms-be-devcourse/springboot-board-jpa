package com.example.springbootboardjpa.repoistory;

import com.example.springbootboardjpa.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserName(String user);

    @Query("select p from Post as p where p.title like %:title%")
    List<Post> findByTitle(@Param("title") String title);
}
