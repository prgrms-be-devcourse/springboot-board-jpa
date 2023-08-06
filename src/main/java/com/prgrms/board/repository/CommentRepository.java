package com.prgrms.board.repository;

import com.prgrms.board.domain.Comment;
import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByUser(Users user);
    List<Comment> findByPost(Post post);
}
