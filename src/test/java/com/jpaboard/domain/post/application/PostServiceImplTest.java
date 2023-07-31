package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.dto.PostCreateRequest;
import com.jpaboard.domain.post.dto.PostResponse;
import com.jpaboard.domain.post.dto.PostUpdateRequest;
import com.jpaboard.domain.user.application.UserService;
import com.jpaboard.domain.user.dto.request.UserCreationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class PostServiceImplTest {

    @TestConfiguration
    @ComponentScan(basePackages = {"com.jpaboard.domain"})
    static class Config{
    }

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    void create_post_test() {
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));

        Long postId = postService.createPost(new PostCreateRequest(userId, "안녕하세요", "본문은 없습니다."));

        assertThat(postId).isNotNull();
    }

    @Test
    void find_post_by_id_test() {
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        Long postId = postService.createPost(new PostCreateRequest(userId, "안녕하세요", "본문은 없습니다."));

        PostResponse storedPost = postService.findPostById(postId);

        assertThat(postId).isEqualTo(storedPost.id());
    }

    @Test
    void find_all_posts_test() {
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        postService.createPost(new PostCreateRequest(userId, "안녕하세요", "본문은 없습니다."));
        postService.createPost(new PostCreateRequest(userId, "안녕하세요2", "본문은 없습니다.2"));

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse> posts = postService.findPosts(pageable);

        assertThat(posts.getTotalElements()).isEqualTo(2);
    }

    @Test
    void find_post_by_title() {
        String title = "타겟";
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        postService.createPost(new PostCreateRequest(userId, title, "본문은 없습니다."));
        postService.createPost(new PostCreateRequest(userId, "안녕하세요2", "본문은 없습니다.2"));

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse> posts = postService.findPostByTitle(title, pageable);

        assertThat(posts.getTotalElements()).isEqualTo(1);
    }

    @Test
    void find_post_by_content() {
        String content = "타겟";
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        postService.createPost(new PostCreateRequest(userId, "제목입니다", "본문은 없습니다."));
        postService.createPost(new PostCreateRequest(userId, "안녕하세요2", content));

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse> posts = postService.findPostByContent(content, pageable);

        assertThat(posts.getTotalElements()).isEqualTo(1);
    }

    @Test
    void find_post_by_keyword() {
        String keyword = "타겟";
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        postService.createPost(new PostCreateRequest(userId, keyword, "본문은 없습니다."));
        postService.createPost(new PostCreateRequest(userId, "안녕하세요2", keyword));

        Pageable pageable = PageRequest.of(0, 10);
        Page<PostResponse> posts = postService.findByKeyword(keyword, pageable);

        assertThat(posts.getTotalElements()).isEqualTo(2);
    }

    @Test
    void update_post_test() {
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        Long postId = postService.createPost(new PostCreateRequest(userId, "제목", "본문은 없습니다."));
        PostUpdateRequest request = new PostUpdateRequest("수정된 제목", "수정된 본문");

        postService.updatePost(postId, request);
        PostResponse updatedPost = postService.findPostById(postId);

        assertThat(updatedPost.title()).isEqualTo(request.title());
        assertThat(updatedPost.content()).isEqualTo(request.content());
    }

    @Test
    void delete_post_test() {
        Long userId = userService.createUser(new UserCreationRequest("tester", 25, "축구"));
        Long postId = postService.createPost(new PostCreateRequest(userId, "제목", "본문은 없습니다."));

        postService.deletePostById(postId);

        assertThatThrownBy(() -> postService.findPostById(postId))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
