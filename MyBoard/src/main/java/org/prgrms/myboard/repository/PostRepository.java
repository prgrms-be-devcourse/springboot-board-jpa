package org.prgrms.myboard.repository;

import org.prgrms.myboard.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedBy(String userName);
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByOrderByIdDesc(Pageable pageable);
    List<Post> findByIdLessThanOrderByIdDesc(Long postId, Pageable pageable);
    boolean existsByIdLessThan(Long cursorId);
}
