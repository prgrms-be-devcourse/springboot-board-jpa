package com.prgrms.board.post.service;

import com.prgrms.board.common.exception.MaxPostException;
import com.prgrms.board.post.domain.Post;
import com.prgrms.board.post.dto.PostRequest;
import com.prgrms.board.post.dto.PostResponse;
import com.prgrms.board.post.repository.PostRepository;
import com.prgrms.board.user.domain.User;
import com.prgrms.board.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    User user;
    Post post1, post2, post3;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
                User.builder()
                        .name("wansu")
                        .age(27L)
                        .hobby("soccer")
                        .build());
        post1 = postRepository.save(
                Post.builder()
                        .title("데브코스")
                        .content("에프팀")
                        .user(user)
                        .build());
        post2 = postRepository.save(
                Post.builder()
                        .title("데브코스")
                        .content("최고")
                        .user(user)
                        .build());
        post3 = postRepository.save(
                Post.builder()
                        .title("스코브데")
                        .content("최고입니다")
                        .user(user)
                        .build());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void 하루_최대_post_초과() {
        PostRequest postRequest = new PostRequest("하루", "포스트량 초과");
        Assertions.assertThatThrownBy(() -> postService.insert(postRequest, user.getUserId()))
                .isInstanceOf(MaxPostException.class);
    }

    @Test
    void slice_조회() {
        Slice<PostResponse> posts = postService.findAllByKeyword("최고", PageRequest.of(0, 4));
        Assertions.assertThat(posts.getNumberOfElements()).isEqualTo(2);
    }

    @Test
    void 빈_keyword로_slice_조회() {
        Slice<PostResponse> posts = postService.findAllByKeyword(null, PageRequest.of(0, 4));
        Assertions.assertThat(posts.getNumberOfElements()).isEqualTo(3);
    }

    @Test
    @Transactional
    void post_업데이트() {
        postService.updateOne(post1.getPostId(), new PostRequest(null, "updatePost"));
        Assertions.assertThat(post1.getTitle()).isEqualTo("데브코스");
        Assertions.assertThat(post1.getContent()).isEqualTo("updatePost");
    }
}