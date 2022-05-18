package com.prgrms.hyuk.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.UserDto;
import com.prgrms.hyuk.repository.PostRepository;
import com.prgrms.hyuk.service.converter.Converter;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class PostServiceIntegrationTest {

    @Autowired
    private PostRepository postRepository;

    private PostService postService;

    @Autowired
    EntityManager em;

    @BeforeAll
    void setUp() {
        postService = new PostService(postRepository, new Converter());
    }

    @Test
    @DisplayName("새로운 사용자가 게시글 작성")
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
}