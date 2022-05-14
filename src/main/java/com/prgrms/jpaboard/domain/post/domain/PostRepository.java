package com.prgrms.jpaboard.domain.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p join fetch p.user",
            countQuery = "select count(p) from Post p join p.user")
    Page<Post> findAllWithUser(Pageable pageable);
}
