package com.springbootboardjpa.post.dto;

import java.util.List;

public record PostsResponse(List<PostWithoutContentResponse> posts) {
}
