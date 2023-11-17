package org.prgms.springbootboardjpayu.repository;

import org.prgms.springbootboardjpayu.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
