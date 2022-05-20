package org.programmers.springbootboardjpa.web.dto.post;

import org.programmers.springbootboardjpa.domain.Post;

public interface PostUpdateForm {
    Post applyToPost(Post post);

    Long postId();
}