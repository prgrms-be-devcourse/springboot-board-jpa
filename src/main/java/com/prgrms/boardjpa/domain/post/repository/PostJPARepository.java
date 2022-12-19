package com.prgrms.boardjpa.domain.post.repository;

import com.prgrms.boardjpa.domain.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJPARepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);
}
