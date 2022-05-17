package com.example.boardjpa.service;

import com.example.boardjpa.domain.User;
import com.example.boardjpa.dto.*;
import com.example.boardjpa.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("Post를 생성하고 조회할 수 있다.")
    void testCreatePost() {
        //given
        String title = "테스트";
        String content = "테스트 입니다.";
        User user = userRepository.save(
                new User(
                        "박상혁"
                        , 25
                        , "음주"
                )
        );

        //when
        CreatePostRequestDto request = new CreatePostRequestDto(
                title
                , content
                , user.getId()
        );
        Long postId = postService.createPost(request).getPostId();

        //then
        PostResponseDto foundPost = postService.getPostById(postId);
        UserResponseDto foundUser = foundPost.getUser();
        assertThat(foundPost.getTitle()).isEqualTo(title);
        assertThat(foundPost.getContent()).isEqualTo(content);
        assertThat(foundUser.getId()).isEqualTo(user.getId());
        assertThat(foundUser.getName()).isEqualTo(user.getName());
        assertThat(foundUser.getAge()).isEqualTo(user.getAge());
        assertThat(foundUser.getHobby()).isEqualTo(user.getHobby());
    }

    @Test
    @DisplayName("Post를 update할 수 있다.")
    void testUpdatePost() {
        //given
        String title = "테스트";
        String content = "테스트 입니다.";
        User user = userRepository.save(
                new User(
                        "박상혁"
                        , 25
                        , "음주"
                )
        );
        Long postId = postService.createPost(new CreatePostRequestDto(
                title
                , content
                , user.getId()
        )).getPostId();

        //when
        String changedContent = "변경된 내용입니다.";
        UpdatePostRequestDto request = new UpdatePostRequestDto(
                changedContent
        );
        postService.updatePost(postId, request);

        //then
        PostResponseDto foundPost = postService.getPostById(postId);
        assertThat(foundPost.getContent()).isEqualTo(changedContent);
    }

    @Test
    @DisplayName("다건의 test를 page로 불러올 수 있다.")
    void testPage() {
        //given
        String title = "테스트";
        String content = "테스트 입니다.";
        User user = userRepository.save(
                new User(
                        "박상혁"
                        , 25
                        , "음주"
                )
        );

        //when
        for (int i = 0; i < 10; i++) {
            Long postId = postService.createPost(new CreatePostRequestDto(
                    title + i
                    , content + i
                    , user.getId()
            )).getPostId();
        }
        PageRequest page = PageRequest.of(0, 10);

        //Then
        PostsResponseDto all = postService.getPosts(page);
        assertThat(all.getPosts()).hasSize(10);
    }
}