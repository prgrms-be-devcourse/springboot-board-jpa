package com.kdt.boardMission.repository;

import com.kdt.boardMission.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p where p.title like %:title%")
    Page<Post> findByTitle(@Param("title") String title, Pageable pageable);

}
