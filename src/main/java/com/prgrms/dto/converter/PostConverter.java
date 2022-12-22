package com.prgrms.dto.converter;

import com.prgrms.domain.post.Post;
import com.prgrms.dto.PostDto.Response;

public class PostConverter {

    private PostConverter() {
    }

    public static Response toPostResponseDto(Post post) {

        return new Response(post.getId(), post.getTitle(), post.getContent(),
            post.getUser().getId(), post.getUser().getName());
    }

}
