package com.ys.board.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.ys.board.common.exception.EntityNotFoundException;
import com.ys.board.domain.post.model.Post;
import com.ys.board.domain.post.api.PostCreateRequest;
import com.ys.board.domain.post.api.PostCreateResponse;
import com.ys.board.domain.post.api.PostResponse;
import com.ys.board.domain.post.api.PostResponses;
import com.ys.board.domain.post.api.PostUpdateRequest;
import com.ys.board.domain.post.repository.PostRepository;
import com.ys.board.domain.user.model.User;
import com.ys.board.domain.user.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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

        Post post = Post.create(user, title, content);
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

        Post post = Post.create(user, title, content);
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
        assertThrows(EntityNotFoundException.class,
            () -> postServiceFacade.updatePost(postId, postUpdateRequest));
    }

    @DisplayName("updateAll 수정 실정 테스트 - Post의 title이나 content가 빈 값이면 수정하지 않고 예외를 던진다")
    @Test
    void updateAllPostFailTestTitleAndContentEmpty() {
        //given
        User user = User.create("name", 28, "");
        userRepository.save(user);

        String title = "title";
        String content = "content";

        Post post = Post.create(user, title, content);
        postRepository.save(post);
        Long postId = post.getId();

        String updateTitle = "";
        String updateContent = "";
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(updateTitle, updateContent);

        //when
        assertThrows(IllegalArgumentException.class,
            () -> postServiceFacade.updatePost(postId, postUpdateRequest));

        //then
        Optional<Post> postOptional = postRepository.findById(postId);
        assertTrue(postOptional.isPresent());
        Post findPost = postOptional.get();
        assertEquals(title, findPost.getTitle());
        assertEquals(content, findPost.getContent());
    }

    @DisplayName("findAllByCursorId 조회 테스트 - cursorId가 존재하고 조회하는 양보다 데이터의 양이 많으면 hasNext가 true, id와 createdAt의 desc로 조회된다")
    @Test
    void findAllByCursorIdSuccessHasNext() {
        //given
        int size = 30;
        List<Post> posts = saveAll(size);
        Long cursorId = posts.get(size / 2).getId();
        int pageSize = 10;

        //when
        PostResponses postResponses = postServiceFacade.findAllPostsByIdCursorBased(
            cursorId, pageSize);

        //then
        assertTrue(postResponses.isHasNext());
        assertEquals(pageSize, postResponses.getPostResponses().size());
        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getPostId)
            .isSortedAccordingTo(Comparator.reverseOrder());

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getCreatedAt)
            .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @DisplayName("findAllByCursorId 조회 테스트 - cursorId가 존재하고 조회하는 양보다 데이터의 양이 적으면 hasNext가 false이고 id와 createdAt의 desc로 조회된다")
    @Test
    void findAllByCursorIdSuccessHasFalse() {
        //given
        int size = 10;
        List<Post> posts = saveAll(size);
        Long cursorId = posts.get(size / 2).getId(); // 중간값의 Id
        int pageSize = 10;

        //when
        PostResponses postResponses = postServiceFacade.findAllPostsByIdCursorBased(
            cursorId, pageSize);

        //then
        assertFalse(postResponses.isHasNext());
        assertThat(postResponses.getPostResponses()).hasSizeLessThan(pageSize);

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getPostId)
            .isSortedAccordingTo(Comparator.reverseOrder());

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getCreatedAt)
            .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @DisplayName("findAllByCursorId 조회 테스트 - cursorId가 존재하지않고 조회하는 양보다 데이터의 양이 많으면  hasNext가 true이고 id와 createdAt의 desc로 조회된다")
    @Test
    void findAllByCursorIdNotExistsSuccessHasTrue() {
        //given
        int size = 20;
        saveAll(size);
        int pageSize = 10;

        //when
        PostResponses postResponses = postServiceFacade.findAllPostsByIdCursorBased(
            null, pageSize);

        //then
        assertTrue(postResponses.isHasNext());
        assertEquals(pageSize, postResponses.getPostResponses().size());

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getPostId)
            .isSortedAccordingTo(Comparator.reverseOrder());

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getCreatedAt)
            .isSortedAccordingTo(Comparator.reverseOrder());
    }

    @DisplayName("findAllByCursorId 조회 테스트 - cursorId가 존재하지않고 조회하는 양보다 데이터의 양이 적으면  hasNext가 false이고 id와 createdAt의 desc로 조회된다")
    @Test
    void findAllByCursorIdNotExistsSuccessHasFalse() {
        //given
        int size = 9;
        saveAll(size);
        int pageSize = 10;

        //when
        PostResponses postResponses = postServiceFacade.findAllPostsByIdCursorBased(
            null, pageSize);

        //then
        assertFalse(postResponses.isHasNext());
        assertThat(postResponses.getPostResponses()).hasSizeLessThan(pageSize);

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getPostId)
            .isSortedAccordingTo(Comparator.reverseOrder());

        assertThat(postResponses.getPostResponses())
            .map(PostResponse::getCreatedAt)
            .isSortedAccordingTo(Comparator.reverseOrder());
    }

    private List<Post> saveAll(int size) {
        User user = User.create("name", 28, "");
        userRepository.save(user);

        List<Post> posts = IntStream.range(0, size)
            .mapToObj(v -> {
                    Post post = new Post(user, "title" + v, "content" + v);
                    return post;
                }
            )
            .collect(Collectors.toList());

        return postRepository.saveAll(posts);
    }

}