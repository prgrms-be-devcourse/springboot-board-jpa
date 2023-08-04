package com.example.jpaboard.post.domain;

import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p " +
            "WHERE (p.title LIKE %:title%) "+
            "AND (p.content LIKE %:contents%)")
    Slice<Post> findPostAllByFilter(@Param("title") String title,
                                    @Param("content") String contents,
                                    Pageable pageable);

}
