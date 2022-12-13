package com.ys.board.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.post.Post;
import com.ys.board.domain.post.PostUpdateRequest;
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
        assertThrows(EntityNotFoundException.class,
            () -> postServiceFacade.createPost(postCreateRequest));
    }

    @DisplayName("findPostById 조회 성공 테스트 - Post 조회에 성공한다")
    @Test
    void findPostByIdSuccess() {
        //given
        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(title, content);
        post.changeUser(user);
        postRepository.save(post);

        //when
        PostResponse postResponse = postServiceFacade.findPostById(post.getId());

        //then
        assertEquals(post.getTitle(), postResponse.getTitle());
        assertEquals(post.getContent(), postResponse.getContent());
        assertEquals(post.getUser().getId(), postResponse.getUserId());
        assertEquals(post.getCreatedAt(), postResponse.getCreatedAt());
        assertEquals(post.getCreatedBy(), postResponse.getCreatedBy());
    }

    @DisplayName("findPostById 조회 실패 테스트 - Post 가 없으므로 조회에 실패하고 예외를 던진다.")
    @Test
    void findPostByIdFailNotFound() {
        //given
        Long postId = 0L;

        //when & then
        assertThrows(EntityNotFoundException.class, () -> postServiceFacade.findPostById(postId));

    }

    @DisplayName("updateAll 수정 성공 테스트 - Post의 title과 content를 수정한다.")
    @Test
    void updateAllPostSuccess() {
        //given
        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(title, content);
        post.changeUser(user);
        postRepository.save(post);
        Long postId = post.getId();

        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);

        //when
        postServiceFacade.updatePost(postId, postUpdateRequest);
        //then
        Optional<Post> postOptional = postRepository.findById(postId);
        assertTrue(postOptional.isPresent());
        Post findPost = postOptional.get();
        assertEquals(updateTitle, findPost.getTitle());
        assertEquals(updateContent, findPost.getContent());
    }

    @DisplayName("updateAll 수정 실패 테스트 - Post 가 없으므로 수정에 실패하고 예외를 던진다.")
    @Test
    void updateAllPostFailNotFound() {
        //given
        Long postId = 0L;

        String updateTitle = "updateTitle";
        String updateContent = "updateContent";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);

        //when & then
        assertThrows(EntityNotFoundException.class, () -> postServiceFacade.updatePost(postId, postUpdateRequest));
    }

    @DisplayName("updateAll 수정 실정 테스트 - Post의 title이나 content가 빈 값이면 수정하지 않고 예외를 던진다")
    @Test
    void updateAllPostFailTestTitleAndContentEmpty() {
        //given
        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(title, content);
        post.changeUser(user);
        postRepository.save(post);
        Long postId = post.getId();

        String updateTitle = "";
        String updateContent = "";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);

        //when
        assertThrows(IllegalArgumentException.class, () -> postServiceFacade.updatePost(postId, postUpdateRequest));

        //then
        Optional<Post> postOptional = postRepository.findById(postId);
        assertTrue(postOptional.isPresent());
        Post findPost = postOptional.get();
        assertEquals(title, findPost.getTitle());
        assertEquals(content, findPost.getContent());
    }


}