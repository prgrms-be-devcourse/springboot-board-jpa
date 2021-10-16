package com.jpa.board.post.service;

import com.jpa.board.post.dto.PostCreateDto;
import com.jpa.board.post.dto.PostReadDto;
import com.jpa.board.post.repository.PostRepository;
import com.jpa.board.user.User;
import com.jpa.board.user.UserRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws NotFoundException{
        userRepository.save(User.builder()
                        .age(30)
                        .name("미니미")
                        .hobby("독서")
                .build());
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .title("서비스 등록 가능?")
                .content("글이 등록되었나??")
                .userId(1L)
                .build();

        String text = postService.save(postCreateDto);

        log.info("return Text: {}", text);
    }

    @Test
    void findAllTest() throws NotFoundException{
        PageRequest page = PageRequest.of(0, 10);

        Page<PostReadDto> all = postService.findAll(page);

        assertThat(all.getTotalElements()).isEqualTo(1);
    }

    @Test
    void findOneTest() throws NotFoundException{
        PostReadDto postDto = postService.findById(1L);

        assertThat(postDto.getId()).isEqualTo(1L);
    }
}