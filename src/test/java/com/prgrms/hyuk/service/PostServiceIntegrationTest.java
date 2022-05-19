package com.prgrms.hyuk.service;

import static com.prgrms.hyuk.exception.ExceptionMessage.INVALID_POST_ID_EXP_MSG;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.UserDto;
import com.prgrms.hyuk.exception.InvalidPostIdException;
import com.prgrms.hyuk.repository.PostRepository;
import com.prgrms.hyuk.service.converter.Converter;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class PostServiceIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    private PostService postService;

    @Autowired
    EntityManager em;

    private User user;

    @BeforeAll
    void setUpBeforeTests() {
        postService = new PostService(postRepository, new Converter());
    }
    
    @BeforeEach
    void setUp() {
        user = new User(new Name("pang"), new Age(26), Hobby.SOCCER);
    }

    @Test
    @DisplayName("게시글 작성")
    void testCreateSuccessWhenNewUserCreatePost() {
        //given
        var postCreateRequest = new PostCreateRequest(
            "this is title...",
            "content",
            new UserDto(
                "eunhyuk",
                26,
                "soccer"
            )
        );

        //when
        var savedId = postService.create(postCreateRequest);

        //then
        var post = postRepository.findById(savedId).get();
        assertAll(
            () -> assertThat(post).isNotNull(),
            () -> assertThat(post.getTitle().getTitle()).isEqualTo(postCreateRequest.getTitle()),

            () -> assertThat(post.getUser()).isNotNull(),
            () -> assertThat(post.getUser().getName().getName())
                .isEqualTo(postCreateRequest.getUserDto().getName())
        );
    }

    @Test
    @DisplayName("게시글 조회 (페이징)")
    void testFindPosts() {
        //given
        var post1 = new Post(
            new Title("this is title ..."),
            new Content("content")
        );
        post1.assignUser(user);

        var post2 = new Post(
            new Title("this is title ..."),
            new Content("content")
        );
        post2.assignUser(user);

        postRepository.saveAll(List.of(post1, post2));

        var pageRequest = PageRequest.of(1, 4);

        //when
        var posts = postService.findPosts(pageRequest);

        //then
        assertThat(posts.getTotalElements()).isEqualTo(2L);
    }

    @Test
    @DisplayName("게시글 단건 조회 - 게시글 존재")
    void testFindByIdWhenExist() {
        //given
        var post = new Post(
            new Title("this is title..."),
            new Content("this is content...")
        );
        post.assignUser(user);

        var savedPost = postRepository.save(post);

        //when
        var retrievedPost = postService.findPost(savedPost.getId());

        //then
        assertThat(retrievedPost.getId()).isEqualTo(savedPost.getId());
    }

    @Test
    @DisplayName("게시글 단건 조회 - 게시글 없음")
    void testFindByIdFailBecauseInvalidPostId() {
        //given
        Long invalidId = 1L;

        //when
        //then
        assertThatThrownBy(() -> postService.findPost(invalidId))
            .isInstanceOf(InvalidPostIdException.class)
            .hasMessageContaining(INVALID_POST_ID_EXP_MSG.getMessage());
    }
}
