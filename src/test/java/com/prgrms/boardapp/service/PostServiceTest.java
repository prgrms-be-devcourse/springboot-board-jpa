package com.prgrms.boardapp.service;

import com.prgrms.boardapp.dto.PostRequest;
import com.prgrms.boardapp.dto.PostResponse;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.model.User;
import com.prgrms.boardapp.repository.PostRepository;
import com.prgrms.boardapp.repository.UserRepository;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.prgrms.boardapp.common.PostCreateUtil.createPost;
import static com.prgrms.boardapp.common.UserCreateUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class PostServiceTest {

    private static Logger log = LoggerFactory.getLogger(PostServiceTest.class);

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    Converter converter;

    User user = createUser();
    Post post = createPost();

    @BeforeEach
    void init() {
        userRepository.save(user);
        postRepository.save(post);
        Post findPost = postRepository.findById(this.post.getId()).get();
        findPost.changeUser(user);
        postRepository.save(findPost);
    }

    @AfterEach
    void destroy() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Description("init data가 정상적으로 저장되었는지 확인한다.")
    void testInit() {
        User findUser = userRepository.findById(user.getId()).get();
        Post findPost = postRepository.findById(post.getId()).get();

        assertThat(findPost.getUser().getId()).isEqualTo(findUser.getId());
    }

    @Test
    @Description("Post 정보를 변경한다.")
    void testUpdate() {
        String updateTitle = "update title";
        String updateContent = "update content";

        PostRequest postDto = PostRequest.builder()
                .title(updateTitle)
                .content(updateContent)
                .build();
        postService.update(post.getId(), postDto);
        Post findPost = postRepository.findById(post.getId()).get();
        assertAll(
                () -> assertThat(findPost.getTitle()).isEqualTo(updateTitle),
                () -> assertThat(findPost.getContent()).isEqualTo(updateContent)
        );
    }

    @Test
    @Description("read test")
    void testFindById() {
        PostResponse postResponse = postService.findById(post.getId());

        assertAll(
                () -> assertThat(postResponse.getId()).isEqualTo(post.getId()),
                () -> assertThat(postResponse.getUser().getId()).isEqualTo(user.getId())
        );
    }
}