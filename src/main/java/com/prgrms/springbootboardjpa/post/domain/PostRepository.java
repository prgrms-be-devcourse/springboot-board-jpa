package com.prgrms.springbootboardjpa.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p")
    Page<Post> findPostPage(Pageable pageable);

}
