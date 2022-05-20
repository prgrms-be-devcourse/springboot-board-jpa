package org.programmers.springbootboardjpa.web.dto.post;

import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.domain.user.User;

public record PostCreateFormV1(String title, String content, Long userId) implements PostCreateForm {

    @Override
    public Post convertToPost(User user) {
        return new Post(title, content, user);
    }
}