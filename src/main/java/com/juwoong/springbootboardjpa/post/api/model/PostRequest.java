package com.juwoong.springbootboardjpa.post.api.model;

public sealed interface PostRequest permits PostRequest.Create, PostRequest.Update {

    record Create(String title, String content) implements PostRequest {
    }

    record Update(String title, String content) implements PostRequest {
    }

}
