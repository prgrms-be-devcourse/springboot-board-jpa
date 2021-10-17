package com.example.springbootboard.service;

import com.example.springbootboard.converter.DtoConverter;
import com.example.springbootboard.dto.PostRequestDto;
import com.example.springbootboard.dto.UserResponseDto;
import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.User;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceTest {

    private UserRepository userRepository = mock(UserRepository.class);
    private PostRepository postRepository = mock(PostRepository.class);
    private DtoConverter dtoConverterMock = mock(DtoConverter.class);

    private PostService postService = new PostService(postRepository, userRepository, dtoConverterMock);

    private DtoConverter dtoConverter = new DtoConverter();


    User user;

    Post post;

    PostRequestDto postRequestDto;

    @BeforeEach
    void setUp() {
        user = dtoConverter.convertUser(UserResponseDto.builder()
                        .id(1L)
                        .name("testUser")
                        .age(26)
                        .postDtos(List.of())
                        .hobby("testHobby")
                        .build());
        postRequestDto = PostRequestDto.builder()
                .userId(1L)
                .title("testTitle")
                .content("testContent")
                .build();

        post = dtoConverter.convertPost(postRequestDto);
    }

    @Test
    void insertTest() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(dtoConverterMock.convertPost(postRequestDto)).thenReturn(post);
        when(postRepository.save(post)).thenReturn(post);
        // when
        Long id = postService.insert(postRequestDto);
        // then
        verify(userRepository).findById(1L);
        verify(dtoConverterMock).convertPost(postRequestDto);
        verify(postRepository).save(post);
    }
}