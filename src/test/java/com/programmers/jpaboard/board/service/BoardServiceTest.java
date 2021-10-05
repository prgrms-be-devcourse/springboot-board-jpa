package com.programmers.jpaboard.board.service;

import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    Member member;

    @BeforeAll
    void setUp(){
        member = Member.builder()
                .age(10)
                .name("name")
                .hobbies(List.of("Table Tennis"))
                .build();

        memberService.saveMember(member);
    }

    @Test
    @DisplayName("게시글을 저장한다")
    public void saveTest() {
        // Given
        Board board = Board.builder()
                .title("title")
                .content("content")
                .build();


        // When
        Board actual = boardService.saveBoard(board, member);

        // Then
        assertThat(actual).isEqualTo(board);
    }
}