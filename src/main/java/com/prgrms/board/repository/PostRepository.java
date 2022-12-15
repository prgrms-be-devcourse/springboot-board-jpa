package com.prgrms.board.repository;

import com.prgrms.board.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByIdDesc(Pageable pageable);

    List<Post> findByIdLessThanOrderByIdDesc(Long cursorId, Pageable pageable);

    boolean existsByIdLessThan(Long id);
}
