package com.kdt.board.repository;

import com.kdt.board.domain.Comment;
import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long > {
    List<Comment> findAllByUser(User user);
}
