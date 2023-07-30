package com.programmers.board.service;

import com.programmers.board.domain.Post;
import com.programmers.board.domain.User;
import com.programmers.board.dto.PostDto;
import com.programmers.board.dto.UserDto;
import com.programmers.board.repository.PostRepository;
import com.programmers.board.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @InjectMocks
    PostService postService;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;

    User givenUser;
    Post givenPost;

    @BeforeEach
    void setUp() {
        givenUser = new User("name", 20, "hobby", "hseong3243");
        givenPost = new Post("title", "content", "hseong3243", givenUser);
    }

    @Nested
    @DisplayName("중첩: 게시글 생성")
    class CreatePostTest {
        @Test
        @DisplayName("성공")
        void createPost() {
            //given
            Long userId = 1L;
            String title = "title";
            String content = "content";

            given(userRepository.findById(any())).willReturn(Optional.ofNullable(givenUser));

            //when
            postService.createPost(userId, title, content);

            //then
            then(postRepository).should().save(any());
        }

        @Test
        @DisplayName("예외: 존재하지 않는 회원")
        void createPost_ButNoSuchUser_Then_Exception() {
            //given
            Long userId = 1L;
            String title = "title";
            String content = "content";

            given(userRepository.findById(any())).willReturn(Optional.empty());

            //when
            //then
            assertThatThrownBy(() -> postService.createPost(userId, title, content))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("성공: 게시글 목록 조회")
    void findPosts() {
        //given
        int page = 0;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        PageImpl<Post> posts = new PageImpl<>(List.of(givenPost), pageRequest, 1);

        given(postRepository.findAll(pageRequest)).willReturn(posts);

        //when
        Page<PostDto> postPage = postService.findPosts(page, size);

        //then
        assertThat(postPage.getContent()).hasSize(1);
    }

    @Nested
    @DisplayName("중첩: 게시글 단건 조회")
    class FindPostTest {
        @Test
        @DisplayName("성공")
        void findPost() {
            //given
            given(postRepository.findById(any())).willReturn(Optional.ofNullable(givenPost));

            //when
            PostDto findPost = postService.findPost(1L);

            //then
            UserDto findUser = findPost.getUserDto();
            assertThat(findPost.getTitle()).isEqualTo(givenPost.getTitle());
            assertThat(findPost.getContent()).isEqualTo(givenPost.getContent());
            assertThat(findUser.getName()).isEqualTo(givenUser.getName());
            assertThat(findUser.getHobby()).isEqualTo(givenUser.getHobby());
            assertThat(findUser.getAge()).isEqualTo(givenUser.getAge());
        }

        @Test
        @DisplayName("예외: 존재하지 않는 게시글")
        void findPost_ButEmpty() {
            //given
            given(postRepository.findById(any())).willThrow(NoSuchElementException.class);

            //when
            //then
            assertThatThrownBy(() -> postService.findPost(any()))
                    .isInstanceOf(NoSuchElementException.class);
        }
    }

    @Test
    @DisplayName("성공: 게시글 수정")
    void updatePost() {
        //given
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        given(postRepository.findById(any())).willReturn(Optional.ofNullable(givenPost));

        //when
        postService.updatePost(1L, updateTitle, updateContent);

        //then
        assertThat(givenPost.getTitle()).isEqualTo(updateTitle);
        assertThat(givenPost.getContent()).isEqualTo(updateContent);
    }

    @Test
    @DisplayName("성공: 게시글 삭제")
    void deletePost() {
        //given
        given(postRepository.findById(any())).willReturn(Optional.ofNullable(givenPost));

        //when
        postService.deletePost(1L);

        //then
        then(postRepository).should().delete(any());
    }
}