package com.kdt.springbootboard.repository;

import com.kdt.springbootboard.domain.post.Post;
import com.kdt.springbootboard.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 메소드 쿼리
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByUser(User user, Pageable pageable);

}
