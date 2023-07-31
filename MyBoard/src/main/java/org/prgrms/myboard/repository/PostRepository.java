package org.prgrms.myboard.repository;

import org.prgrms.myboard.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    String CURSOR_SQL = "SELECT * FROM posts WHERE post_id <= :postId ORDER BY post_id LIMIT :pageSize";

    List<Post> findAllByCreatedBy(String userName);
    List<Post> findAllByUserId(Long userId);
    List<Post> findAllByOrderByIdDesc(Pageable pageable);
    @Query(value = CURSOR_SQL, nativeQuery = true)
    Slice<Post> findByIdLessThanOrderByIdDescLimitByPageSize(Long postId, int pageSize);
    Page<Post> findAll(Pageable pageable);
}
