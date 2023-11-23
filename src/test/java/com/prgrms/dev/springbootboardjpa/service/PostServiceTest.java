package com.prgrms.dev.springbootboardjpa.service;

import com.prgrms.dev.springbootboardjpa.domain.post.PostRepository;
import com.prgrms.dev.springbootboardjpa.domain.user.Hobby;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import com.prgrms.dev.springbootboardjpa.domain.user.UserRepository;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostRequestDto;
import jakarta.persistence.EntityNotFoundException;
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
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    private PostDto postDto;
    private User user;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();

        user = User.builder()
                .name("박씨")
                .age(22)
                .hobby(Hobby.SPORTS).build();

        userRepository.save(user);

        PostRequestDto postRequestDto = PostRequestDto.builder()
                .title("테스트")
                .content("테스트 콘")
                .build();

        postDto = postService.create(postRequestDto, user.getId());
    }

    @Test
    @DisplayName("게시글이 존재하는 경우")
    void find_success_test() {
        PostDto findDto = postService.findById(postDto.id());
        assertThat(findDto.id()).isEqualTo(postDto.id());
    }

    @Test
    @DisplayName("게시글이 존재하지 않는 경우")
    void find_fail_test() {
        assertThrows(EntityNotFoundException.class, () -> postService.findById(100L));
    }

    @Test
    void getAll() {
        PageRequest page = PageRequest.of(0, 10);
        Page<PostDto> findDtos = postService.getAll(page);
        assertThat(findDtos.getTotalElements()).isEqualTo(1L);
    }

    @Test
    void update() {
        PostRequestDto requestDto = PostRequestDto.builder()
                .title("수정된 이름")
                .content("수정된 콘텐츠")
                .build();
        Long id = postDto.id();
        PostDto updatedDto = postService.update(requestDto, id);
        assertThat(updatedDto.title()).isEqualTo(requestDto.title());
        assertThat(updatedDto.content()).isEqualTo(requestDto.content());
    }
}
