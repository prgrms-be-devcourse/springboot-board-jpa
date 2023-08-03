package com.jpaboard.domain.post.infrastructure;

import com.jpaboard.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.title like %:title% or p.content like %:content% or p.title like %:keyword% or p.content like %:keyword%")
    Page<Post> findAllByCondition(String title, String content, String keyword, Pageable pageable);

}
