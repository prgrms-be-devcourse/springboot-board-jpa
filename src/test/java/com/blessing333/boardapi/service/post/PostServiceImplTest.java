package com.blessing333.boardapi.service.post;

import com.blessing333.boardapi.TestDataProvider;
import com.blessing333.boardapi.controller.dto.PostCreateCommands;
import com.blessing333.boardapi.controller.dto.PostInformation;
import com.blessing333.boardapi.converter.PostConverter;
import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.entity.exception.PostCreateFailException;
import com.blessing333.boardapi.repository.PostRepository;
import com.blessing333.boardapi.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostServiceImplTest {
    @Autowired
    private PostConverter postConverter;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestDataProvider dataProvider;
    private PostService postService;
    private User defaultUser;

    @BeforeAll
    void init() {
        postService = new PostServiceImpl(postRepository, userRepository, postConverter);
        defaultUser = dataProvider.insertUserToDb("tester", 20);
    }

    @DisplayName("id로 게시글 정보를 조회하여 PostInformation으로 반환한다")
    @Test
    void loadPostById() {
        String title = "title";
        String content = "content";
        Post post = dataProvider.insertPostToDb(title, content, defaultUser);

        PostInformation postInformation = postService.loadPostById(post.getId());

        assertThat(postInformation.getId()).isEqualTo(post.getId());
        assertThat(postInformation.getTitle()).isEqualTo(post.getTitle());
        assertThat(postInformation.getContent()).isEqualTo(post.getContent());
        assertThat(postInformation.getWriter()).isEqualTo(post.getWriter());
    }

    @DisplayName("존재하지 않는 게시글 조회를 시도하면 PostNotFoundException 발생")
    @Test
    void loadPostWithInvalidId() {
        assertThrows(PostNotFoundException.class, () -> postService.loadPostById(-1L));
    }

    @DisplayName("게시글 생성 테스트")
    @Test
    void registerPostTest() {
        PostCreateCommands postCreateCommands = new PostCreateCommands("title", "content", defaultUser.getId());

        PostInformation postInformation = postService.registerPost(postCreateCommands);

        assertTrue(postRepository.existsById(postInformation.getId()));
    }

    @DisplayName("잘못된 UserId로 게시글 생성 요청시 PostCreateFailException 생성")
    @Test
    void registerPostWithInvalidUserId() {
        Long invalidUserId = -1L;
        PostCreateCommands postCreateCommands = new PostCreateCommands("title", "content", invalidUserId);

        assertThrows(PostCreateFailException.class, () -> postService.registerPost(postCreateCommands));
    }

}