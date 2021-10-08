package com.programmers.jpaboard.board.service;

import com.programmers.jpaboard.board.controller.dto.BoardUpdateDto;
import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.repository.BoardRepository;
import com.programmers.jpaboard.member.domain.Member;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private BoardRepository boardRepository;

    private final Member member;
    private final Board board;

    public BoardServiceTest() {
        member = Member.builder()
                .age(10)
                .name("name")
                .hobbies(List.of("Table Tennis"))
                .build();

        // Given
        board = Board.builder()
                .title("SetupTitle")
                .content("SetupContent")
                .build();
    }

    @Test
    @DisplayName("게시글을 저장한다")
    public void saveTest() {
        // Given
        BoardService boardService = new BoardService(boardRepository);
        when(boardRepository.save(board)).thenReturn(board);

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
        // Given
        BoardService boardService = new BoardService(boardRepository);
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

        // When
        Board actual = boardService.findOne(board.getId());

        // Then
        assertThat(actual).isEqualTo(board);
    }

    @Test
    @DisplayName("모든 게시글을 조회한다")
    public void findAllTest() {
        // Given
        BoardService boardService = new BoardService(boardRepository);

        Board newBoard = Board.builder()
                .title("newTitle")
                .content("newContent")
                .build();
        boardService.saveBoard(newBoard, member);
        when(boardRepository.findAll()).thenReturn(List.of(board, newBoard));

        // When
        List<Board> all = boardService.findAll();

        // Then
        assertThat(all).containsExactlyInAnyOrder(newBoard, board);
    }

    @Test
    @DisplayName("게시글을 수정한다")
    public void updateTest() {
        // Given
        BoardService boardService = new BoardService(boardRepository);

        BoardUpdateDto boardUpdateDto = new BoardUpdateDto();
        boardUpdateDto.setContent("updatedContent");
        boardUpdateDto.setTitle("updatedTitle");

        when(boardRepository.save(board)).thenReturn(board);
        when(boardRepository.findById(board.getId())).thenReturn(Optional.of(board));

        boardService.saveBoard(board, member);

        // When
        Board actual = boardService.updateBoard(board.getId(), boardUpdateDto);

        // Then
        assertThat(actual).isEqualTo(board);
    }
}