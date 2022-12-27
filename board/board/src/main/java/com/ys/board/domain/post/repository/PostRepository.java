package com.ys.board.domain.post.repository;

import com.ys.board.domain.post.model.Post;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p")
    Slice<Post> findAllByPageable(Pageable pageable);

    @Query("select p from Post p where p.id < :id")
    Slice<Post> findAllByCursorId(@Param("id") long id, Pageable pageable);

}
