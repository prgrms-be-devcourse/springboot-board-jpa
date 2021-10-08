package org.prgms.board.domain.repository;

import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(User user);
}
