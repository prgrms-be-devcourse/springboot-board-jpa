package com.prgrms.board.repository;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUser(Pageable pageable, User user);

}
