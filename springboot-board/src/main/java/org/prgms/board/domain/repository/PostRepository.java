package org.prgms.board.domain.repository;

import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByWriter(Pageable pageable, User user);
}
