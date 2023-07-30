package devcource.hihi.boardjpa.dto;

import devcource.hihi.boardjpa.domain.Post;

public record CreatePostDto(String title, String content) {

    public Post toEntity() {
        return new Post(title, content);
    }

}

