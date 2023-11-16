package com.example.board.service;

import com.example.board.dto.PostDto;
import com.example.board.exception.BaseException;
import com.example.board.exception.ErrorMessage;
import com.example.board.model.Post;
import com.example.board.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @AfterEach
    void clear(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("정상적으로 PostDto를 받아 글 작성을 완료하는 경우")
    void postSuccessInService(){
        PostDto postDto = new PostDto(1, "test", "test Contents");

        Long save = postService.save(postDto);
        Optional<Post> savedPosts = postRepository.findById(save);

        Assertions.assertThat(savedPosts).isNotEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 유저가 글을 작성하는 경우 예외를 발생시킨다.")
    void postFailWithAnonymousUser(){
        PostDto postDto = new PostDto(2, "test", "test Contents");

        Assertions.assertThatThrownBy(() ->postService.save(postDto))
                .isInstanceOf(BaseException.class)
                .hasMessage(ErrorMessage.USER_NOT_FOUND.getMessage());
    }

}