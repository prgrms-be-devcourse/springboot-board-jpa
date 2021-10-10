package com.example.springbootboard.service;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.Title;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.dto.*;
import com.example.springbootboard.repository.PostRepository;
import com.example.springbootboard.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@SpringBootTest
public class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    RequestUser requestUser = null;

    @BeforeAll
    void setUp() {
        requestUser = RequestUser.builder()
                .age(27)
                .name("seunghun")
                .hobby("SPORTS")
                .build();
    }

    @Test
    public void testAutowired() throws Exception {
        assertThat(postService).isNotNull();
        assertThat(userRepository).isNotNull();
    }
    
    @Test
    @DisplayName("게시글이 저장된다")
    public void testPostSave() throws Exception {
        //given
        RequestCreatePost request = RequestCreatePost.builder()
                .content("content")
                .title("title")
                .requestUser(requestUser)
                .build();

        //when
        Long postId = postService.save(request);

        //then
        Optional<Post> actual = postRepository.findById(postId);
        assertThat(actual).isPresent();
        assertThat(actual.get().getId()).isEqualTo(postId);
        assertThat(actual.get().getContent()).isEqualTo(request.getContent());
        assertThat(actual.get().getTitle()).isEqualTo(new Title(request.getTitle()));
    }

    @Test 
    @DisplayName("게시글을 단건 조회한다")
    public void testFindOne() throws Exception {
        //given
        RequestCreatePost request = RequestCreatePost.builder()
                .content("content")
                .title("title")
                .requestUser(requestUser)
                .build();

        Long postId = postService.save(request);

        //when
        ResponsePost response = postService.findOne(postId);

        //then
        assertThat(response.getId()).isEqualTo(postId);
        assertThat(response.getContent()).isEqualTo(request.getContent());
        assertThat(response.getTitle()).isEqualTo(request.getTitle());
    } 
    
    @Test 
    @DisplayName("게시글을 모두 조회한다")
    public void testFindAll() throws Exception {
        //given
        RequestCreatePost request1 = RequestCreatePost.builder()
                .content("content1")
                .title("title1")
                .requestUser(requestUser)
                .build();

        RequestCreatePost request2 = RequestCreatePost.builder()
                .content("content2")
                .title("title2")
                .requestUser(requestUser)
                .build();

        RequestCreatePost request3 = RequestCreatePost.builder()
                .content("content3")
                .title("title3")
                .requestUser(requestUser)
                .build();

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        postService.save(request1);
        postService.save(request2);
        postService.save(request3);

        //then
        ResponsePagePost actual = postService.findAll(pageRequest);
        assertThat(actual.getPosts()).hasSize(3);
        log.info("page: {}", actual);
    }

    @Test
    @DisplayName("게시글을 수정한다")
    public void testUpdatePost() throws Exception {
        //given
        RequestCreatePost requestCreate = RequestCreatePost.builder()
                .content("content")
                .title("title")
                .requestUser(requestUser)
                .build();

        Long postId = postService.save(requestCreate);

        RequestUpdatePost requestUpdate = RequestUpdatePost.builder()
                .title("update title")
                .content("update content")
                .build();

        //when
        Long id = postService.update(postId, requestUpdate);

        //then
        Optional<Post> actual = postRepository.findById(postId);
        assertThat(actual).isPresent();
        assertThat(actual.get().getContent()).isEqualTo("update content");
        assertThat(actual.get().getTitle()).isEqualTo(new Title("update title"));
    }
}
