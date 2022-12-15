package com.assignment.board.service;

import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
import com.assignment.board.dto.post.PostUpdateDto;
import com.assignment.board.entity.Hobby;
import com.assignment.board.entity.User;
import com.assignment.board.repository.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostService postService;

    User user;
    User savedUser;
    PostRequestDto postRequestDto;
    PostResponseDto createdPostResponseDto;

    @BeforeEach
    void setUp() throws NotFoundException {
        user = new User();
        user.setName("김소현");
        user.setAge(23);
        user.setHobby(Hobby.SWIM);
        savedUser = userRepository.save(user);

        postRequestDto = new PostRequestDto();
        postRequestDto.setTitle("테스트");
        postRequestDto.setContent("테스트 입니다.");
        postRequestDto.setUserId(savedUser.getId());

        createdPostResponseDto = postService.createPost(postRequestDto);
    }


    @Test
    @DisplayName("단건 조회")
    void testGetPostById() throws NotFoundException {
        PostResponseDto foundPostDto = postService.getPostById(createdPostResponseDto.getId());
        assertThat(foundPostDto.getTitle()).isEqualTo(createdPostResponseDto.getTitle());
    }

    @Test
    @DisplayName("페이징 조회")
    void testGetAllPost() throws NotFoundException {
        PostRequestDto postRequestDto2 = new PostRequestDto();
        postRequestDto2.setTitle("테스트2");
        postRequestDto2.setContent("테스트 입니다.");
        postRequestDto2.setUserId(savedUser.getId());
        postService.createPost(postRequestDto2);

        PostRequestDto postRequestDto3 = new PostRequestDto();
        postRequestDto3.setTitle("테스트3");
        postRequestDto3.setContent("테스트 입니다.");
        postRequestDto3.setUserId(savedUser.getId());
        postService.createPost(postRequestDto3);

        PageRequest page = PageRequest.of(0, 2);
        Page<PostResponseDto> all = postService.getAllPost(page);
        assertThat(all.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("게시글 수정")
    void testUpdatePost() throws NotFoundException {
        PostUpdateDto postUpdateDto = new PostUpdateDto();
        postUpdateDto.setId(createdPostResponseDto.getId());
        postUpdateDto.setTitle("수정 테스트");
        postUpdateDto.setContent("수정합니다");

        PostResponseDto postResponseDto = postService.updatePost(postUpdateDto);

        assertThat(postResponseDto.getTitle()).isEqualTo(postUpdateDto.getTitle());
    }

}