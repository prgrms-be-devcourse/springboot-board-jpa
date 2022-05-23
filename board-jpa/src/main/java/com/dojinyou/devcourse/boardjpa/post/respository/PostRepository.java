package com.dojinyou.devcourse.boardjpa.post.respository;

import com.dojinyou.devcourse.boardjpa.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
