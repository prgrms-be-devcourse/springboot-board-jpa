package com.devcourse.bbs.repository.post;

import com.devcourse.bbs.domain.post.Post;
import com.devcourse.bbs.domain.user.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    List<Post> findAllByUser(User user);
}
