package com.programmers.jpaboard.board.service;

import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.repository.BoardRepository;
import com.programmers.jpaboard.member.domain.Member;
import com.programmers.jpaboard.member.repository.MemberRepository;
import com.programmers.jpaboard.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    Member member;
    Board board;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .age(10)
                .name("name")
                .hobbies(List.of("Table Tennis"))
                .build();

        memberService.saveMember(member);


        // Given
        board = Board.builder()
                .title("SetupTitle")
                .content("SetupContent")
                .build();

        boardService.saveBoard(board, member);
    }

    @AfterEach
    void tearDown(){
        boardRepository.deleteAll();
        memberRepository.deleteAll();
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

    @Test
    @DisplayName("게시글 한 개를 조회한다.")
    public void findOneTest() {
        // When
        Board actual = boardService.findOne(board.getId());

        // Then
        assertThat(actual).isEqualTo(board);
    }

    @Test
    @DisplayName("모든 게시글을 조회한다")
    public void findAllTest() {
        // Given
        Board newBoard = Board.builder()
                .title("newTitle")
                .content("newContent")
                .build();
        boardService.saveBoard(newBoard, member);

        // When
        List<Board> all = boardService.findAll();

        // Then
        assertThat(all).containsExactlyInAnyOrder(newBoard, board);
    }

    @Test
    @DisplayName("게시글을 수정한다")
    public void updateTest() {
        Board newBoard = Board.builder()
                .title("updatedTitle")
                .content("updatedContent")
                .build();

        boardService.saveBoard(board, member);

        // When
        Board actual = boardService.updateBoard(board.getId(), newBoard);

        // Then
        assertThat(actual).isEqualTo(board);
    }
}