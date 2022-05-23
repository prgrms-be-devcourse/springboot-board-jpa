package com.devcourse.springjpaboard.post.service;

import com.devcourse.springjpaboard.model.post.Post;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.converter.PostConverter;
import com.devcourse.springjpaboard.post.repository.PostRepository;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.user.controller.dto.CreateUserRequest;
import com.devcourse.springjpaboard.user.controller.dto.UserResponse;
import com.devcourse.springjpaboard.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;


    @InjectMocks
    private PostService postService;


    @BeforeEach
    void beforeEach() {

    }

    @Test
    @DisplayName("게시글 저장 테스트")
    void saveTest() {

    }

    @Test
    @DisplayName("게시글 번호로 게시글을 찾을 수 있는지 테스트")
    void findOneTest() {

    }

    @Test
    @DisplayName("지정한 페이지만큼 게시글을 찾는지 테스트")
    void findAllTest() {

    }

    @Test
    @DisplayName("게시글 업데이트가 정상적으로 되는지 테스트")
    void updateTest() {

    }
}