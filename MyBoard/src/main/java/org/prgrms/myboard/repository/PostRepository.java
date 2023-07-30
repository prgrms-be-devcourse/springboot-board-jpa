package org.prgrms.myboard.repository;

import org.prgrms.myboard.domain.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCreatedBy(String userName);
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByOrderByIdDesc(Pageable pageable);
    @Query(value = "SELECT * FROM posts WHERE post_id <= :postId AND post_id >= :postId - :pageSize" +
        " ORDER BY post_id LIMIT :pageSize", nativeQuery = true)
    Slice<Post> findByIdLessThanOrderByIdDescLimitByPageSize(Long postId, int pageSize);
    boolean existsByIdLessThan(Long cursorId);
    boolean existsByIdAfter(Long cursorId);
}
