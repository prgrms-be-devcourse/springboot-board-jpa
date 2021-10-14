package org.prgrms.springbootboard.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByWriter(User writer);

    Page<Post> findAllByWriter(User writer, Pageable pageable);
}
