package com.devcourse.bbs.repository;

import com.devcourse.bbs.domain.user.Post;
import com.devcourse.bbs.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
