package org.programmers.project_board.converter;

import org.programmers.project_board.dto.PostDto;
import org.programmers.project_board.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    private final UserConverter userConverter;

    public PostConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    // dto -> entity
    public Post convertPost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());

        post.setUser(userConverter.convertUser(postDto.getUserDto()));

        return post;
    }

    // entity -> dto
    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(userConverter.convertUserDto(post.getUser()))
                .build();
    }

}
