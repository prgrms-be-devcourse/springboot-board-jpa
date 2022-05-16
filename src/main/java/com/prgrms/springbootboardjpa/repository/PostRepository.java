package com.prgrms.springbootboardjpa.repository;

import com.prgrms.springbootboardjpa.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
