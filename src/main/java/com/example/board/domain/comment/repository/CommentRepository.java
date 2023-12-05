package com.example.board.domain.comment.repository;

import com.example.board.domain.comment.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
        "left join fetch c.parent m " +
        "where c.post.id =:postId order by m.id asc nulls first, c.id asc")
    List<Comment> findAllOrderByParentIdAscNullsFirstByPost(@Param("postId") Long postId);
}
