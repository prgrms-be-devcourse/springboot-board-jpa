package com.kdt.postproject.post.service;

import com.kdt.postproject.domain.post.PostRepository;
import com.kdt.postproject.post.dto.PostDto;
import com.kdt.postproject.post.dto.UserDto;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        //Given
        PostDto postDto = PostDto.builder()
                .title("test")
                .content("testing....")
                .userDto(
                        UserDto.builder()
                                .name("bnminji")
                                .age(26)
                                .hobby("running")
                                .build()
                )
                .build();
        //When
        Long id = postService.save(postDto);

        //Then
        log.info("{}", id);
    }

//    @AfterEach
//    void tearDown() {
//        postRepository.deleteAll();
//    }

    @Test
    void updateTest() throws NotFoundException {
        // Given
        PostDto updateDto = PostDto.builder()
                .id(1L)
                .title("update")
                .content("updating....")
                .userDto(
                        UserDto.builder()
                                .name("bnminji")
                                .age(26)
                                .hobby("running")
                                .build()
                )
                .build();
        // When
        PostDto one = postService.update(updateDto);
        // Then
        assertThat(one.getTitle()).isEqualTo(updateDto.getTitle());
    }

    @Test
    void findOneTest() throws NotFoundException {
        // Given
        Long id = 1L;
        // When
        PostDto one = postService.findOne(id);
        // Then
        assertThat(one.getId()).isEqualTo(id);
    }

    @Test
    void findAllTest() {
        // Given
        PageRequest page = PageRequest.of(0, 10);
        // When
        Page<PostDto> all = postService.findAll(page);
        // Then
        assertThat(all.getTotalElements()).isEqualTo(1L);
    }
}