package kdt.prgms.springbootboard.converter;

import kdt.prgms.springbootboard.domain.Post;
import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.dto.PostDetailResponseDto;
import kdt.prgms.springbootboard.dto.PostSaveRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    private final UserConverter userConverter;

    public PostConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    public Post convertPost(PostSaveRequestDto postSaveRequestDto, User user) {
        return new Post(
            postSaveRequestDto.getTitle(),
            postSaveRequestDto.getContent(),
            user
        );
    }

    public PostSaveRequestDto convertPostDto(Post post) {
        return new PostSaveRequestDto(
            post.getId(),
            post.getTitle(),
            post.getContent(),
            userConverter.convertUserDto(post.getUser())
        );
    }

    public PostDetailResponseDto convertPostDetailDto(Post post) {
        return new PostDetailResponseDto(
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
