package com.prgrms.hyuk.repository;

import com.prgrms.hyuk.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {

}
