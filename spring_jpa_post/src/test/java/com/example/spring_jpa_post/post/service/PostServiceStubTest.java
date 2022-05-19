package com.example.spring_jpa_post.post.service;

import com.example.spring_jpa_post.post.dto.request.CreatePostRequest;
import com.example.spring_jpa_post.post.dto.request.ModifyPostRequest;
import com.example.spring_jpa_post.post.dto.response.FoundPostResponse;
import com.example.spring_jpa_post.post.entity.Post;
import com.example.spring_jpa_post.post.exception.PostNotFoundException;
import com.example.spring_jpa_post.post.repository.PostRepository;
import com.example.spring_jpa_post.user.entity.Hobby;
import com.example.spring_jpa_post.user.entity.User;
import com.example.spring_jpa_post.user.exception.UserNotFoundException;
import com.example.spring_jpa_post.user.repository.UserRepository;
import com.example.spring_jpa_post.user.service.UserMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceStubTest {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PostMapper postMapper;

    CreatePostRequest createPostRequest;
    Long savedPostId;

    @BeforeEach
    void setup() {
        User savedUser = userRepository.save(User.builder().name("강용수").age(27).hobby(Hobby.FOOTBALL).build());
        createPostRequest = CreatePostRequest.builder().userId(savedUser.getId()).title("title").content("content").build();
        savedPostId = postService.createPost(createPostRequest);
    }

    @AfterEach
    void cleanup(){
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("게시판을 생성 성공.")
    void createPostTest() {
        //then
        Optional<Post> foundPost = postRepository.findById(savedPostId);
        assertThat(foundPost).isPresent();
    }

    @Test
    @DisplayName("user 레포에 저장되지 않은 Id를 가진 post는 저장 실패")
    void failCreatePostTest() {
        //given
        Long notSavedUserId = -1L;
        createPostRequest.setUserId(notSavedUserId);
        //when then
        assertThrows(UserNotFoundException.class, () -> postService.createPost(createPostRequest));
    }

    @Test
    @DisplayName("특정 게시판을 조회 성공.")
    void getPostTest() {
        //when
        FoundPostResponse foundPost = postService.getPostById(savedPostId);
        //then
        assertThat(foundPost).isNotNull();
    }

    @Test
    @DisplayName("존재하지 않는 id 로는 특정 게시판을 조회 실패.")
    void failGetPostTest() {
        //given
        Long notSavedPostId = -1L;
        //when then
        assertThrows(PostNotFoundException.class, () -> postService.getPostById(notSavedPostId));
    }

    @Test
    @DisplayName("모든 게시글을 조회 성공")
    void getAllPostTest() {
        //given
        Pageable pageable = PageRequest.of(0, 10);
        //when
        Page<FoundPostResponse> allPost = postService.getAllPost(pageable);
        //then
        assertThat(allPost).isNotEmpty();
    }

    @Test
    @DisplayName("특정 게시글 수정 성공")
    void name() {
        //given
        ModifyPostRequest modifyPostRequest = ModifyPostRequest.builder().title("update_title").content("update_content").build();
        //when
        Long modifiedPostId = postService.modifyPost(savedPostId, modifyPostRequest);
        //then
        Post modifiedPost = postRepository.findById(modifiedPostId).get();
        assertThat(modifiedPost.getTitle()).isEqualTo(modifyPostRequest.getTitle());
        assertThat(modifiedPost.getContent()).isEqualTo(modifyPostRequest.getContent());
    }
}