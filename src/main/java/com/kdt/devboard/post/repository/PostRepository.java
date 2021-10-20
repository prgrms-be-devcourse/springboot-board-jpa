package com.kdt.devboard.post.repository;

import com.kdt.devboard.post.domain.Post;
import com.kdt.devboard.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    void deleteAllByUser(User user);

}


