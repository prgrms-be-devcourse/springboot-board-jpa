package org.prgrms.myboard.repository;

import org.prgrms.myboard.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    String CURSOR_SQL = "SELECT * FROM posts WHERE post_id <= :cursorId ORDER BY post_id LIMIT :pageSize";

    List<Post> findAllByCreatedBy(String userName);

    List<Post> findAllByUserId(Long userId);

    @Query(value = CURSOR_SQL, nativeQuery = true)
    List<Post> findByIdLessThanOrderByIdLimitByPageSize(Long cursorId, int pageSize);

    boolean existsByIdAfter(Long cursorId);

    boolean existsByIdBefore(Long cursorId);
}
