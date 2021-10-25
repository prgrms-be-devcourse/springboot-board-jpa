package com.kdt.board.repository;

import com.kdt.board.domain.Comment;
import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.post.PostResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
