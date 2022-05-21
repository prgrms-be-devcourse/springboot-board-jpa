package com.devcourse.springjpaboard.post.repository;

import com.devcourse.springjpaboard.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
