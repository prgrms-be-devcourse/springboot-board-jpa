package com.prgrms.hyuk.repository;

import com.prgrms.hyuk.domain.post.Post;
import java.util.List;

public interface PostCustomRepository {

    List<Post> findAll(int offset, int limit);
}
