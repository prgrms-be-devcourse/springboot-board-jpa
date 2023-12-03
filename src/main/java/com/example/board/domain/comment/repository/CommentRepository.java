package com.example.board.domain.comment.repository;

import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select c from Comment c " +
        "left join fetch c.parent m " +
        "where c.post.id =:postId order by m.id asc nulls first, c.id asc")
    List<Comment> findAllOrderByParentIdAscNullsFirstByPost(@Param("postId") Long postId);

    List<Comment> findByWriter(Member member);

}
