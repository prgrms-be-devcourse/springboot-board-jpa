package com.kdt.board.domain.service;

import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.dto.UserDto;
import com.kdt.board.domain.repository.PostRepository;
import com.kdt.board.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;
    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    Long savedUserId;
    Long savedPostId;

    @AfterEach
    void tearDown() {
        postRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @BeforeEach
    void saveTest() {
        // given
        UserDto.SaveRequest userDto = new UserDto.SaveRequest("YongHoon", 26, "tennis");
        savedUserId = userService.save(userDto);

        PostDto.SaveRequest postDto = new PostDto.SaveRequest("제목테스트", "내용내용내용내용", savedUserId);

        // when
        savedPostId = postService.save(postDto);
    }

    @Test
    @DisplayName("Id 값으로 Post Entity 를 찾아온다.")
    void findByIdTest() {
        // given

        // when
        PostDto.Response findPostDto = postService.findById(savedPostId);

        // then
        assertThat(findPostDto.title()).isEqualTo("제목테스트");
        assertThat(findPostDto.content()).isEqualTo("내용내용내용내용");
        assertThat(findPostDto.user().id()).isEqualTo(savedUserId);
    }

    @Test
    @DisplayName("모든(All) Post Entity 를 찾아온다.")
    void findAllTest() {
        // given
        PageRequest page = PageRequest.of(0, 10);

        // when
        Page<PostDto.Response> all = postService.findAll(page);

        // then
        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    @DisplayName("Id 값으로 Post Entity 를 삭제한다.")
    void deleteByIdTest() {
        // given

        // when
        postService.deleteById(savedPostId);

        // then
        assertThat(postRepository.count()).isEqualTo(0L);
    }


    @Test
    @DisplayName("Post Entity 를 update 한다.")
    void updateTest() {
        // given
        PostDto.UpdateRequest changedDto = new PostDto.UpdateRequest(savedPostId, "수정된 타이틀", "수정된 내용내용", savedUserId);

        // when
        postService.update(changedDto);
        PostDto.Response updatedDto = postService.findById(savedPostId);

        // then
        assertThat(updatedDto.title()).isEqualTo("수정된 타이틀");
        assertThat(updatedDto.content()).isEqualTo("수정된 내용내용");
        assertThat(updatedDto.user().id()).isEqualTo(savedUserId);
    }
}