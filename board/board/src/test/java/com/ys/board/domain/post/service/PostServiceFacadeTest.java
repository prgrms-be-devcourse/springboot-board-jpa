package com.ys.board.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.post.Post;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.api.PostCreateResponse;
import com.ys.board.domain.post.repository.PostRepository;
import com.ys.board.domain.user.User;
import com.ys.board.domain.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class PostServiceFacadeTest {

    @Autowired
    private PostServiceFacade postServiceFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @DisplayName("createPost 성공 테스트 - Post 생성에 성공한다.")
    @Test
    void createPostSuccess() {
        //given
        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";
        long userId = user.getId();
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);
        //when
        PostCreateResponse postCreateResponse = postServiceFacade.createPost(postCreateRequest);

        //then
        Optional<Post> postOptional = postRepository.findById(postCreateResponse.getPostId());

        assertTrue(postOptional.isPresent());
        Post findPost = postOptional.get();

        assertEquals(user.getId(), findPost.getUser().getId());

        assertThat(findPost)
            .hasFieldOrPropertyWithValue("title", title)
            .hasFieldOrPropertyWithValue("content", content);
    }

    @DisplayName("createPost 실패 테스트 - User가 없는 유저여서 Post 생성에 실패한다.")
    @Test
    void createPostFailNotFoundUser() {
        //given
        String title = "title";
        String content = "content";
        long userId = 1L;
        PostCreateRequest postCreateRequest = new PostCreateRequest(title, content, userId);

        //when & then
        assertThrows(EntityNotFoundException.class, () -> postServiceFacade.createPost(postCreateRequest));
    }

}