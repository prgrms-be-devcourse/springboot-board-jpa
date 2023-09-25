package org.prgrms.myboard.repository;

import org.prgrms.myboard.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    String CURSOR_SQL = "SELECT * FROM posts WHERE id >= :cursorId ORDER BY id LIMIT :pageSize";
    List<Post> findAllByCreatedBy(String userName);

    List<Post> findAllByOrderByIdAsc(Pageable pageable);

    @Query(value = CURSOR_SQL, nativeQuery = true)
    List<Post> findByIdHigherThanOrderByIdLimitByPageSize(Long cursorId, int pageSize);

    List<Post> findAllByUserId(Long userId);

    boolean existsPostByIdAfter(Long postId);
}
