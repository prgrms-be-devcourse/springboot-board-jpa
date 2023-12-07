package com.prgrms.dev.springbootboardjpa.service;

import com.prgrms.dev.springbootboardjpa.domain.post.PostRepository;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import com.prgrms.dev.springbootboardjpa.domain.user.UserRepository;
import com.prgrms.dev.springbootboardjpa.dto.PostCreateRequestDto;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostUpdateRequestDto;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest // 굳이 모든 bean을 긁어오지 말고 필요한 값들만 가져와보자
class PostServiceTest {

    // yml 파일 test 분리, db qn

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
                .hobby("SPORTS").build();

        userRepository.save(user);

        PostCreateRequestDto postRequestDto = PostCreateRequestDto.builder()
                .userId(user.getId())
                .title("테스트")
                .content("테스트 콘")
                .build();

        postDto = postService.create(postRequestDto);
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

    // 테스트 대상을 명확하게 구분해보자 , ex update 대상 존재 x
    @Test
    void update() {
        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
                .title("수정된 이름")
                .content("수정된 콘텐츠")
                .build();
        Long id = postDto.id();
        PostDto updatedDto = postService.update(requestDto, id);
        assertThat(updatedDto.title()).isEqualTo(requestDto.title());
        assertThat(updatedDto.content()).isEqualTo(requestDto.content());
    }
}
