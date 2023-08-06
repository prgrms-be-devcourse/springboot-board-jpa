package com.kdt.devcourse.module.post.presentation.dto;

import static com.kdt.devcourse.module.post.presentation.dto.CreateOrUpdatePost.Request;
import static com.kdt.devcourse.module.post.presentation.dto.CreateOrUpdatePost.Response;

public sealed interface CreateOrUpdatePost permits Request, Response {
    record Request(String title, String content) implements CreateOrUpdatePost {
    }

    record Response(Long id, String title, String content) implements CreateOrUpdatePost {
    }
}
