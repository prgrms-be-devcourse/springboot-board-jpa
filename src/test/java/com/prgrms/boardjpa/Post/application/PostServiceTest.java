package com.prgrms.boardjpa.Post.application;

import com.prgrms.boardjpa.Post.domain.PostRepository;
import com.prgrms.boardjpa.Post.dto.request.PostCreateRequest;
import com.prgrms.boardjpa.Post.dto.request.PostUpdateRequest;
import com.prgrms.boardjpa.Post.dto.response.PostListResponse;
import com.prgrms.boardjpa.Post.dto.response.PostResponse;
import com.prgrms.boardjpa.User.domain.User;
import com.prgrms.boardjpa.User.domain.UserRepository;
import com.prgrms.boardjpa.global.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    PostCreateRequest postRequest;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("hong")
                .age(10)
                .hobby("computer")
                .build();
        User savedUser = userRepository.save(user);

        postRequest = PostCreateRequest.builder()
                .title("제목")
                .content("내용")
                .userId(savedUser.getId())
                .build();
    }

    @Test
    @DisplayName("Post생성 성공")
    void createPostSuccessTest() throws Exception {

        //when
        PostResponse postResponse = postService.create(postRequest);

        //then
        assertThat(postResponse.title()).isEqualTo(postRequest.getTitle());
        assertThat(postResponse.content()).isEqualTo(postRequest.getContent());
        assertThat(postResponse.authorId()).isEqualTo(postRequest.getUserId());

        User user = userRepository.findById(postResponse.authorId()).get();
        assertThat(user.getPosts().get(0).getTitle()).isEqualTo(postRequest.getTitle());
    }

    @Test
    @DisplayName("Post 생성 실패 - 존재하지 않는 유저")
    void createPostFailTest() throws Exception {

        //given
        PostCreateRequest invalidRequest = PostCreateRequest.builder()
                .title("제목")
                .content("내용")
                .userId(10L)
                .build();

        //when -> then
        assertThrows(BusinessException.class,
                () -> postService.create(invalidRequest));
    }

    @Test
    @DisplayName("게시물 단건 조회 성공")
    void findPostByIdSuccessTest() throws Exception {

        //given
        PostResponse postResponse = postService.create(postRequest);

        //when
        PostResponse foundPost = postService.findOne(postResponse.id());

        //then
        assertThat(foundPost.title()).isEqualTo(postResponse.title());
        assertThat(foundPost.content()).isEqualTo(postResponse.content());
    }

    @Test
    @DisplayName("게시물 단건 조회 실패 - 존재하지 않음")
    void findPostByIdFailTest() throws Exception {

        //given
        Long invalidId = 10L;

        //when -> then
        assertThrows(BusinessException.class,
                () -> postService.findOne(invalidId));
    }

    @Test
    @DisplayName("게시물 전체 조회")
    void findAllPostsTest() throws Exception {

        //given
        PostResponse postResponse = postService.create(postRequest);

        //when
        PostListResponse postListResponse = postService.findAll();

        //then
        assertThat(postListResponse.postResponseList()).isNotNull();
        assertThat(postListResponse.postResponseList()).hasSize(1);

        PostResponse foundPost = postListResponse.postResponseList().get(0);
        assertThat(foundPost.title()).isEqualTo(postResponse.title());
    }

    @Test
    @DisplayName("게시물 업데이트 성공")
    void updatePostSuccessTest() throws Exception {

        //given
        PostResponse postResponse = postService.create(postRequest);
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("제목변경", "내용변경");

        //when
        PostResponse updatedPost = postService.update(postResponse.id(), postUpdateRequest);

        //then
        assertThat(updatedPost.title()).isEqualTo(postUpdateRequest.title());
        assertThat(updatedPost.content()).isEqualTo(postUpdateRequest.content());

        User user = userRepository.findById(updatedPost.authorId()).get();
        assertThat(user.getPosts().get(0).getTitle()).isEqualTo(postUpdateRequest.title());
    }

    @Test
    @DisplayName("게시물 업데이트 실패 - 존재하지 않는 게시물")
    void updatePostFailTest_post() throws Exception {

        //given
        Long invalidPostId = 10L;
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest("제목변경", "내용변경");

        //when -> then
        assertThrows(BusinessException.class,
                () -> postService.update(invalidPostId, postUpdateRequest));
    }
}