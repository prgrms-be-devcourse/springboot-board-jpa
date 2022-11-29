package com.springboard.service;

import com.springboard.common.exception.FindFailException;
import com.springboard.post.dto.CreatePostRequest;
import com.springboard.post.dto.CreatePostResponse;
import com.springboard.post.dto.FindPostResponse;
import com.springboard.post.dto.UpdatePostRequest;
import com.springboard.post.repository.PostRepository;
import com.springboard.post.service.PostService;
import com.springboard.user.dto.UserRequest;
import com.springboard.user.dto.UserResponse;
import com.springboard.user.repository.UserRepository;
import com.springboard.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Post Service 테스트")
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("findOne() : 게시글 조회 테스트")
    class FindOneTest {
        UserResponse user;
        CreatePostRequest request;

        @BeforeEach
        void setUp() {
            user = userService.save(new UserRequest("moosong", 25, "비올라"));
            request = new CreatePostRequest(user.id(), "title", "content");
        }

        @Test
        @DisplayName("성공 : 존재하는 PK 값으로 게시글을 조회한 경우")
        void success() {
            // Given
            CreatePostResponse expectedResponse = postService.save(request);
            // When
            FindPostResponse actualResponse = postService.findOne(expectedResponse.id());
            // Then
            assertThat(actualResponse.user().id()).isEqualTo(expectedResponse.user().id());
            assertThat(actualResponse.title()).isEqualTo(expectedResponse.title());
            assertThat(actualResponse.content()).isEqualTo(expectedResponse.content());
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 PK 값으로 게시글을 조회한 경우")
        void fail() {
            // Given
            postService.save(request);
            // When
            Throwable response = catchThrowable(() -> postService.findOne(0L));
            // Then
            assertThat(response).isInstanceOf(FindFailException.class);
        }
    }

    @Nested
    @DisplayName("findAll() : 게시글 목록 조회 테스트")
    class FindAllTest {
        List<CreatePostRequest> expectedList = new ArrayList<>();

        @BeforeEach
        void setUp() {
            UserResponse user1 = userService.save(new UserRequest("moosong", 25, "비올라"));
            CreatePostRequest request1 = new CreatePostRequest(user1.id(), "title1", "content1");
            expectedList.add(request1);
            UserResponse user2 = userService.save(new UserRequest("kate", 15, "플룻"));
            CreatePostRequest request2 = new CreatePostRequest(user2.id(), "title2", "content2");
            expectedList.add(request2);
        }

        @Test
        @DisplayName("성공 : 저장된 게시글이 있는 경우 페이지 요청에 따른 반환")
        void success() {
            // Given
            expectedList.forEach(request -> postService.save(request));
            // When
            PageRequest page = PageRequest.of(0, expectedList.size());
            Page<FindPostResponse> actualList = postService.findAll(page);
            // Then
            assertThat(actualList.getTotalElements()).isEqualTo(expectedList.size());
        }

        @Test
        @DisplayName("실패 : 저장된 게시글이 없는 경우 빈 페이지를 반환")
        void fail() {
            // Given, When
            PageRequest page = PageRequest.of(0, expectedList.size());
            Page<FindPostResponse> actualList = postService.findAll(page);
            // Then
            assertThat(actualList.get().count()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("save() : 게시글 저장 테스트")
    class SaveTest {
        UserResponse user;
        CreatePostRequest request;

        @BeforeEach
        void setUp() {
            user = userService.save(new UserRequest("moosong", 25, "비올라"));
            request = new CreatePostRequest(user.id(), "title", "content");
        }

        @Test
        @DisplayName("성공 : 게시글 생성 DTO에 모든 값이 채워진 경우")
        void success() {
            // Given
            CreatePostResponse expectedResponse = postService.save(request);
            // When
            FindPostResponse actualResponse = postService.findOne(expectedResponse.id());
            // Then
            assertThat(actualResponse.user().id()).isEqualTo(expectedResponse.user().id());
            assertThat(actualResponse.title()).isEqualTo(expectedResponse.title());
            assertThat(actualResponse.content()).isEqualTo(expectedResponse.content());
            assertThat(actualResponse.createdAt()).isNotNull();
            assertThat(actualResponse.updatedAt()).isNotNull();
        }

        @Test
        @DisplayName("실패 : 게시글 생성 DTO의 null 비허용 인자가 null인 경우")
        void fail() {
            // Given
            CreatePostRequest nullRequest = new CreatePostRequest(null, null, null);
            // When
            Throwable response = catchThrowable(() ->postService.save(nullRequest));
            // Then
            assertThat(response).isInstanceOf(InvalidDataAccessApiUsageException.class);
        }
    }

    @Nested
    @DisplayName("updateOne() : 게시글 수정 테스트")
    class UpdateOneTest {
        UserResponse originUser;
        CreatePostRequest originRequest;

        @BeforeEach
        void setUp() {
            originUser = userService.save(new UserRequest("moosong", 25, "비올라"));
            originRequest = new CreatePostRequest(originUser.id(), "title", "content");
        }

        @Test
        @DisplayName("성공 : 존재하는 PK에 대한 수정을 요정하는 경우")
        void success() {
            // Given
            CreatePostResponse origin = postService.save(originRequest);
            // When
            UpdatePostRequest request = new UpdatePostRequest("title11", "content11");
            FindPostResponse expectedResponse = postService.updateOne(origin.id(), request);
            // Then
            FindPostResponse actualResponse = postService.findOne(origin.id());
            assertThat(actualResponse.title()).isEqualTo(expectedResponse.title());
            assertThat(actualResponse.content()).isEqualTo(expectedResponse.content());
            assertThat(actualResponse.updatedAt()).isAfter(expectedResponse.updatedAt());
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 PK에 대한 수정을 요정하는 경우")
        void fail() {
            // Given
            UpdatePostRequest request = new UpdatePostRequest("title11", "content11");
            // When
            Throwable response = catchThrowable(() -> postService.updateOne(0L, request));
            // Then
            assertThat(response).isInstanceOf(FindFailException.class);
        }
    }

    @Nested
    @DisplayName("deleteOne() : 게시글 삭제 테스트")
    class DeleteOneTest {
        CreatePostResponse origin;

        @BeforeEach
        void setUp() {
            UserResponse user = userService.save(new UserRequest("moosong", 25, "비올라"));
            CreatePostRequest request = new CreatePostRequest(user.id(), "title", "content");
            origin = postService.save(request);
        }

        @Test
        @DisplayName("성공 : 존재하는 PK 값으로 삭제한 경우")
        void success() {
            // Given, When
            postService.deleteOne(origin.id());
            // Then
            Throwable response = catchThrowable(() -> postService.findOne(origin.id()));
            assertThat(response).isInstanceOf(FindFailException.class);
        }

        @Test
        @DisplayName("실패 : 존재하지 않는 PK 값으로 삭제한 경우")
        void fail() {
            // Given, When
            Throwable response = catchThrowable(() -> postService.deleteOne(0L));
            // Then
            assertThat(response).isInstanceOf(EmptyResultDataAccessException.class);
        }
    }
}