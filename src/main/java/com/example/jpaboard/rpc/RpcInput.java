package com.example.jpaboard.rpc;

import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.service.PostService;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class RpcInput {

    private final PostService postService;

    public void create(Map<String, String> request) {

        var title = request.get("title");

        var content = request.get("content");

        Post.create(title, content, null);
    }

}
