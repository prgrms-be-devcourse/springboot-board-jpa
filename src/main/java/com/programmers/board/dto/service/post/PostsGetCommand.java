package com.programmers.board.dto.service.post;

import com.programmers.board.dto.request.PostsGetRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsGetCommand {
    private final int page;
    private final int size;

    public static PostsGetCommand from(PostsGetRequest request) {
        return new PostsGetCommand(
                request.getPage(),
                request.getSize()
        );
    }

    public static PostsGetCommand of(int page, int size) {
        return new PostsGetCommand(
                page,
                size
        );
    }

    public Pageable getPageable() {
        return PageRequest.of(page, size);
    }
}
