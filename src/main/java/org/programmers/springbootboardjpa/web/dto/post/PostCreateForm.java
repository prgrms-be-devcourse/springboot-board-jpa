package org.programmers.springbootboardjpa.web.dto.post;

import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.domain.user.User;

public interface PostCreateForm {
    Post convertToPost(User user);

    Long userId();
}
