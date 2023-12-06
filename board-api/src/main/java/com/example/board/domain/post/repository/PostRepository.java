package com.example.board.domain.post.repository;

import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

    @Transactional
    @Modifying
    @Query("DELETE FROM Post p WHERE p.id IN :postIds")
    void deletePostsByIds(List<Long> postIds);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value ="3000")})
    @Query("select p from Post p where p.id = :postId")
    Optional<Post> findByIdWithPessimisticLock(@Param("postId") Long postId);
}
