package com.prgrms.board.service;

import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.request.MemberCreateDto;
import com.prgrms.board.dto.request.PostCreateDto;
import com.prgrms.board.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class PostPagingTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Autowired
    MemberService memberService;

    private final Pageable page = PageRequest.of(0, 10);
    private final int numOfDummyPosts = 20;

    @BeforeEach
    void setup() {
        postRepository.deleteAll();

        MemberCreateDto memberDto = MemberCreateDto.builder()
                .name("testUser")
                .age(26)
                .hobby("농구").build();

        Long joinId = memberService.join(memberDto);

        IntStream.rangeClosed(1, numOfDummyPosts)
                .forEach(i -> {
                    PostCreateDto dto = PostCreateDto.builder()
                            .title("title" + i)
                            .content("content" + i)
                            .writerId(joinId)
                            .build();

                    postService.register(dto);
                });
    }

    @Test
    @DisplayName("게시글을 페이징해서 조회할 수 있다.")
    void 조회_페이징() {
        CursorResult firstSelection = postService.findAll(null, page);
        Long lastIndexOfFirst = firstSelection.getPostDtoList().get(page.getPageSize() - 1).getId();

        assertThat(firstSelection.getPostDtoList().size()).isEqualTo(page.getPageSize());
        assertTrue(firstSelection.getHasNext());

        CursorResult secondSelection = postService.findAll(lastIndexOfFirst, page);
        assertThat(secondSelection.getPostDtoList().size()).isEqualTo(page.getPageSize());
        assertFalse(secondSelection.getHasNext());
    }

}
