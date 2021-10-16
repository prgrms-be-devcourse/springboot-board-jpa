package com.programmers.iyj.springbootboard.domain.post.mapper;

import com.programmers.iyj.springbootboard.domain.post.domain.Post;
import com.programmers.iyj.springbootboard.domain.post.dto.PostDto;
import com.programmers.iyj.springbootboard.domain.user.domain.Hobby;
import com.programmers.iyj.springbootboard.domain.user.domain.User;
import com.programmers.iyj.springbootboard.domain.user.dto.UserDto;
import com.programmers.iyj.springbootboard.domain.user.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class PostMapperTest {
    private final PostMapper mapper
            = Mappers.getMapper(PostMapper.class);

    @Test
    public void entityToDto() {
        // Given
        User user = User.builder()
                .name("john")
                .age(25)
                .hobby(Hobby.NETFLIX)
                .build();

        Post post = Post.builder()
                .title("title1")
                .content("content1")
                .user(user)
                .build();

        // When
        PostDto postDto = mapper.entityToDto(post);

        // Then
        assertEquals(post.getTitle(), postDto.getTitle());
        assertEquals(post.getContent(), postDto.getContent());
    }

    @Test
    public void dtoToEntity() {
        // Given
        PostDto postDto = PostDto.builder()
                .title("title1")
                .content("content1")
                .build();

        // When
        Post post = mapper.dtoToEntity(postDto);

        // Then
        assertEquals(postDto.getTitle(), post.getTitle());
        assertEquals(postDto.getContent(), post.getContent());
    }
}