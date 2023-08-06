package com.programmers.jpaboard.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "from")
public class PostIdResponse {

    private Long postId;
}
