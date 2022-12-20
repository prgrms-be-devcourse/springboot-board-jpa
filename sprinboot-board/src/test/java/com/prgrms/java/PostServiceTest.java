package com.prgrms.java;

import com.prgrms.dto.CreatePostRequest;
import com.prgrms.dto.PostUserInfo;
import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.Post;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.GetPostDetailsResponse;
import com.prgrms.java.dto.GetPostsResponse;
import com.prgrms.java.repository.PostRepository;
import com.prgrms.java.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
    }

    @DisplayName("게시글을 페이징 조회할 수 있다.")
    @Test
    void getPostsTest() {
        // given
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));
        Post post1 = new Post("데브코스 짱짱", "데브코스 짱짱입니다.", user);
        Post post2 = new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user);
        List<Post> posts = postRepository.saveAll(List.of(post1, post2));
        GetPostsResponse expected = new GetPostsResponse(
                posts.stream()
                        .map(GetPostDetailsResponse::from)
                        .toList()
        );

        // when
        GetPostsResponse actual = postService.getPosts(PageRequest.of(0, 10));

        // then
        assertThat(actual.getPostDetails())
                .containsAll(expected.getPostDetails());
    }

    @DisplayName("게시글을 단건 조회할 수 있다.")
    @Test
    void getPostDetailTest() {
        // given
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));
        Post post = postRepository.save(new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user));
        GetPostDetailsResponse expected = GetPostDetailsResponse.from(post);

        // when
        GetPostDetailsResponse actual = postService.getPostDetail(post.getId());

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("게시글을 등록할 수 있다.")
    @Test
    void createPostTest() {
        // given
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));

        CreatePostRequest createPostRequest = new CreatePostRequest("데브코스 짱짱", "데브코스 짱짱입니다.",
                new PostUserInfo(user.getId(), "이택승", 25, "MOVIE")
        );

        // when
        long postId = postService.addPost(createPostRequest);

        // then
        Optional<Post> maybePost = postRepository.findById(postId);
        assertThat(maybePost)
                .isPresent();
    }

    @DisplayName("게시글을 수정할 수 있다.")
    @Test
    void modifyPostTest() {
        // given
        User user = userRepository.save(new User("이택승", 25, HobbyType.MOVIE));
        Post post = postRepository.save(new Post("데브코스 짱짱", "데브코스 짱짱입니다.", user));

        ModifyPostRequest request = new ModifyPostRequest(post.getId(), "데브코스 좋아", "데브코스 좋아용!");

        // when
        postService.modifyPost(request);

        // then
        Optional<Post> maybePost = postRepository.findById(post.getId());
        assertThat(maybePost.get().getTitle())
                .isEqualTo(request.getTitle());
        assertThat(maybePost.get().getContent())
                .isEqualTo(request.getContent());
    }
}