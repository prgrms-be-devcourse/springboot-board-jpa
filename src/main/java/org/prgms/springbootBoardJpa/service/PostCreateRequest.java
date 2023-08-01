package org.prgms.springbootBoardJpa.service;

import org.prgms.springbootBoardJpa.domain.Post;

public record PostCreateRequest(
    UserInfo userInfo,
    String title,
    String content
) {
    public Post toEntity() {
        return new Post(title, content, userInfo.toEntity());
    }
}
