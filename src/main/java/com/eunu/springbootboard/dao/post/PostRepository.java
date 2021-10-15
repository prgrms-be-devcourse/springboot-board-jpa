package com.eunu.springbootboard.dao.post;

import com.eunu.springbootboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
