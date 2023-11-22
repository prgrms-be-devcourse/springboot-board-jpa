package org.programmers.dev.domain.post.infrastructure;

import org.programmers.dev.domain.post.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
