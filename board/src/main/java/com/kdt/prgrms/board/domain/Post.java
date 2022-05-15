package com.kdt.prgrms.board.domain;

import com.kdt.prgrms.board.dto.PostAddRequest;

public class Post {

    private final long id;
    private final String title;
    private final String content;

    private Post(long id, String title, String content) {

        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static Post from(PostAddRequest request) {

        return new Post(0, request.getTitle(), request.getContent());
    }
}
