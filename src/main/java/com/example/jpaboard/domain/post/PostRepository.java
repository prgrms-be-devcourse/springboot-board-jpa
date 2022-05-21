package com.example.jpaboard.domain.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findWithPagination(Pageable pageable);

    Optional<Post> findById(@Param("id") Long id);
}
