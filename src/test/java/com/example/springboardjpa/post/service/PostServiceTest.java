package com.example.springboardjpa.post.service;

import com.example.springboardjpa.post.dto.PostDto;
import com.example.springboardjpa.user.dto.UserDto;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    private long id;

    @BeforeEach
    void save() {
        PostDto postDto = new PostDto(
                10L,
                "게시판 테스트 제목",
                "안녕하세요!",
                new UserDto(
                        1L,
                        "둘리",
                        3,
                        "호잇"
                )
        );
        long saveEntity = postService.save(postDto);
        id = saveEntity;
        assertThat(saveEntity).isNotZero();
    }

    @Test
    @DisplayName("id 값으로 Post 가져오기")
    void findOneTest() throws NotFoundException {
        long targetId = id;
        PostDto one = postService.findOne(targetId);
        assertThat(one.getId()).isEqualTo(targetId);
    }

    @Test
    @DisplayName("Page 전체 가져오기")
    void findAllTest() {

        for (int i = 1; i < 5; i++) {
            PostDto postDto = new PostDto(
                    10L + i,
                    "게시판 테스트 제목"+ i,
                    "안녕하세요!" + i,
                    new UserDto(
                            1L,
                            "둘리",
                            3,
                            "호잇"
                    )
            );
            postService.save(postDto);
        }

        PageRequest page = PageRequest.of(0, 10);

        Page<PostDto> all = postService.findAll(page);

        assertThat(all.getTotalElements()).isEqualTo(5);
    }

    @Test
    @DisplayName("Post 수정하기")
    void updateTest() throws NotFoundException {
        PostDto postDto = new PostDto(
                10L,
                "게시판 바뀐 제목",
                "수정한 안녕하세요!",
                new UserDto(
                        1L,
                        "둘리",
                        3,
                        "호잇"
                )
        );
        postService.update(postDto,id);
        PostDto one = postService.findOne(id);
        assertThat(postDto.getTitle()).isEqualTo(one.getTitle());
        assertThat(postDto.getContent()).isEqualTo(one.getContent());
    }
}