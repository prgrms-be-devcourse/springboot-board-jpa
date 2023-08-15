package com.jpaboard.post.ui;

import static com.jpaboard.post.ui.PostUpdate.Response;
import static com.jpaboard.post.ui.PostUpdate.Request;

public sealed interface PostUpdate permits Request, Response {
    record Request(String title, String content) implements PostUpdate {
    }

    record Response(Long id, String title, String content) implements PostUpdate {
    }
}
