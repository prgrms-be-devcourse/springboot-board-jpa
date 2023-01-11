package com.prgrms.jpa.repository;

import com.prgrms.jpa.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
    List<Post> findByIdLessThanOrderByIdDescCreatedAtDesc(long id, Pageable pageable);
}
