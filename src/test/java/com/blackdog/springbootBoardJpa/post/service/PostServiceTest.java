package com.blackdog.springbootBoardJpa.post.service;

import com.blackdog.springbootBoardJpa.domain.post.service.PostService;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostCreateRequest;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponse;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponses;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostUpdateRequest;
import com.blackdog.springbootBoardJpa.domain.user.model.User;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Age;
import com.blackdog.springbootBoardJpa.domain.user.model.vo.Name;
import com.blackdog.springbootBoardJpa.domain.user.repository.UserRepository;
import com.blackdog.springbootBoardJpa.global.exception.PostNotFoundException;
import com.blackdog.springbootBoardJpa.global.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    static User savedUser;

    @BeforeEach
    void setup() {
        User user = User.builder()
                .name(new Name("홍길동"))
                .age(new Age(1200))
                .hobby("동에번쩍하는거")
                .build();
        savedUser = userRepository.save(user);
    }

    @ParameterizedTest
    @DisplayName("존재하는 유저로 게시글을 생성하면 성공한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void savePost_Dto_SaveReturnResponse(PostCreateRequest request) {
        // when
        PostResponse savedPost = postService.savePost(savedUser.getId(), request);

        // then
        PostResponse result = postService.findPostById(savedPost.id());
        assertThat(result.title()).isEqualTo(savedPost.title());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 유저로 게시글을 생성하면 실패한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void savePost_Dto_Exception(PostCreateRequest request) {
        // given
        Long userId = Long.MAX_VALUE;

        // when
        Exception exception = catchException(() -> postService.savePost(userId, request));

        // then
        assertThat(exception).isInstanceOf(UserNotFoundException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 수정하면 성공한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void updatePost_Dto_UpdateReturnResponse(PostCreateRequest request) {
        // given
        PostResponse savedPost = postService.savePost(savedUser.getId(), request);
        PostUpdateRequest updateRequest = new PostUpdateRequest("수정제목", "수정본문");

        // when
        PostResponse updatePost = postService.updatePost(savedUser.getId(), savedPost.id(), updateRequest);

        // then
        PostResponse result = postService.findPostById(updatePost.id());
        assertThat(result.title()).isEqualTo(updatePost.title());
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 삭제하면 성공한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void deletePostById_Dto_Delete(PostCreateRequest request) {
        // given
        PostResponse savedPost = postService.savePost(savedUser.getId(), request);

        // when
        postService.deletePostById(savedUser.getId(), savedPost.id());

        // then
        assertThatThrownBy(() -> postService.findPostById(savedPost.id()))
                .isInstanceOf(PostNotFoundException.class);
    }

    @ParameterizedTest
    @DisplayName("모든 게시글을 조회하면 성공한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void findAllPosts_Void_ReturnResponses(PostCreateRequest request) {
        // given
        postService.savePost(savedUser.getId(), request);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        PostResponses result = postService.findAllPosts(pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.postResponses()).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 조회하면 성공한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void findPostById_id_ReturnResponse(PostCreateRequest request) {
        // given
        PostResponse savedPost = postService.savePost(savedUser.getId(), request);

        // when
        PostResponse result = postService.findPostById(savedPost.id());

        // then
        assertThat(result.title()).isEqualTo(savedPost.title());
    }

    @ParameterizedTest
    @DisplayName("존재하지 않는 게시글을 조회하면 실패한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void findPostById_id_Exception(PostCreateRequest request) {

        // when
        Exception exception = catchException(() -> postService.findPostById(100L));

        // then
        assertThat(exception).isInstanceOf(PostNotFoundException.class);
    }

    @ParameterizedTest
    @DisplayName("존재하는 게시글을 조회하면 성공한다.")
    @ArgumentsSource(value = PostCreateRequestDataProvider.class)
    void findPostsByUserId_id_ReturnResponse(PostCreateRequest request) {
        // given
        PostResponse savedPost = postService.savePost(savedUser.getId(), request);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        PostResponses result = postService.findPostsByUserId(savedUser.getId(), pageable);

        // then
        assertThat(result).isNotNull();
        assertThat(result.postResponses()).isNotEmpty();
    }

    static class PostCreateRequestDataProvider implements ArgumentsProvider {

        static List<PostCreateRequest> postCreateRequests = List.of(
                new PostCreateRequest("subject1", "content1"),
                new PostCreateRequest("subject2", "content2")
        );

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
            return postCreateRequests.stream()
                    .map(Arguments::of);
        }

    }

}
