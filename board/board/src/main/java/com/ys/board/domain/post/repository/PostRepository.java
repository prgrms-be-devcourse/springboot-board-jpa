package com.ys.board.domain.post.repository;

import com.ys.board.domain.post.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByIdDescCreatedAtDesc(Pageable pageable);

    List<Post> findByIdLessThanOrderByIdDescCreatedAtDesc(@Param("id") long id, Pageable pageable);

}
