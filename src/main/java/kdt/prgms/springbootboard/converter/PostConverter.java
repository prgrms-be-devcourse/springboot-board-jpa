package kdt.prgms.springbootboard.converter;

import kdt.prgms.springbootboard.domain.Post;
import kdt.prgms.springbootboard.dto.PostDetailDto;
import kdt.prgms.springbootboard.dto.PostDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    private final UserConverter userConverter;

    public PostConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public Post convertPost(PostDto postDto) {
        var user = userConverter.convertUser(postDto.getUserDto());
        return Post.createPost(
            postDto.getTitle(),
            postDto.getContent(),
            user);
    }

    public PostDto convertPostDto(Post post) {
        return new PostDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            userConverter.convertUserDto(post.getUser())
        );
    }

    public PostDetailDto convertPostDetailDto(Post post) {
        return new PostDetailDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            post.getCreatedBy(),
            post.getLastModifiedDate(),
            post.getLastModifiedBy(),
            post.getLastModifiedDate(),
            userConverter.convertUserDto(post.getUser())
        );
    }

}
