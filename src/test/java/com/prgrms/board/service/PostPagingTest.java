package com.prgrms.board.service;

import com.prgrms.board.dto.CursorResult;
import com.prgrms.board.dto.MemberCreateDto;
import com.prgrms.board.dto.PostCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PostPagingTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberService memberService;

    private Pageable page = PageRequest.of(0, 10);
    private int numOfDummyPosts = 20;

    @BeforeEach
    void setup() {
        MemberCreateDto memberDto = MemberCreateDto.builder()
                .name("testUser ")
                .age(26)
                .hobby("농구").build();

        Long joinId = memberService.join(memberDto);

        for (int i = 1; i <= numOfDummyPosts; i++) {
            PostCreateDto dto = PostCreateDto.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .writerId(joinId)
                    .build();

            postService.register(dto);
        }
    }

    @Test
    @Transactional
    @DisplayName("게시글을 페이징해서 조회할 수 있다.")
    void 조회_페이징() {
        CursorResult firstSelection = postService.findAll(null, page);
        Long lastIndexOfFirst = firstSelection.getPostDtoList().get(page.getPageSize() - 1).getId();

        assertThat(lastIndexOfFirst).isEqualTo(numOfDummyPosts - page.getPageSize() + 1);
        assertThat(firstSelection.getPostDtoList().size()).isEqualTo(page.getPageSize());
        assertTrue(firstSelection.getHasNext());

        CursorResult secondSelection = postService.findAll(lastIndexOfFirst, page);
        Long lastIndexOfSecond = secondSelection.getPostDtoList().get(page.getPageSize() - 1).getId();

        assertThat(lastIndexOfSecond).isEqualTo(1);
        assertThat(secondSelection.getPostDtoList().size()).isEqualTo(page.getPageSize());
        assertFalse(secondSelection.getHasNext());
    }

}
