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

//@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;


    @DisplayName("게시글을 페이징 조회할 수 있다.")
    @Test
    void getPosts() {
        // given
        User user = userRepository.save(new User(1L, "이택승", 25, HobbyType.MOVIE));
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
    void getPostDetail() {
        // given
        User user = userRepository.save(new User(1L, "이택승", 25, HobbyType.MOVIE));
        Post post = new Post("데브코스 짱짱2", "데브코스 짱짱2입니다.", user);
        Post savedPost = postRepository.save(post);
        GetPostDetailsResponse expected = GetPostDetailsResponse.from(savedPost);

        // when
        GetPostDetailsResponse actual = postService.getPostDetail(1L);

        // then
        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("게시글을 등록할 수 있다.")
    @Test
    void createPost() {
        // given
        userRepository.save(new User(1L, "이택승", 25, HobbyType.MOVIE));

        CreatePostRequest createPostRequest = new CreatePostRequest("데브코스 짱짱", "데브코스 짱짱입니다.",
                new PostUserInfo(1L, "이택승", 25, "MOVIE")
        );

        // when
        postService.addPost(createPostRequest);

        // then
        Optional<Post> maybePost = postRepository.findById(1L);
        assertThat(maybePost).isPresent();

    }
}