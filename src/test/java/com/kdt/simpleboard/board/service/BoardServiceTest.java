package com.kdt.simpleboard.board.service;

import com.kdt.simpleboard.board.domain.Board;
import com.kdt.simpleboard.board.repository.BoardRepository;
import com.kdt.simpleboard.common.dto.PageResponse;
import com.kdt.simpleboard.common.exception.CustomException;
import com.kdt.simpleboard.common.exception.ErrorCode;
import com.kdt.simpleboard.data.BoardData;
import com.kdt.simpleboard.user.UserData;
import com.kdt.simpleboard.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;


import java.util.List;
import java.util.Optional;

import static com.kdt.simpleboard.board.dto.BoardRequest.*;
import static com.kdt.simpleboard.board.dto.BoardResponse.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @Mock
    private BoardRepository boardRepository;

    @InjectMocks
    private BoardService boardService;

    @Mock
    private UserService userService;


    @Test
    @DisplayName("게시판 글 생성에 성공한다.")
    void createBoardSuccess() {
        CreateBoardRequest createBoardRequest = BoardData.createBoardRequest();
        Board board = BoardData.board();

        when(boardRepository.save(any(Board.class))).thenReturn(board);
        when(userService.getUserEntity(any(Long.class))).thenReturn(UserData.user());

        CreateBoardResponse createBoardResponse = boardService.createBoard(createBoardRequest);

        assertEquals(board.getId(), createBoardResponse.createdId());
    }

    @Test
    @DisplayName("게시물 수정에 성공한다.")
    void update() {
        Board board = BoardData.board();
        ReflectionTestUtils.setField(board, "id", 1L);
        ModifyBoardRequest modifyBoardRequest = BoardData.modifyBoardRequest();
        when(boardRepository.findById(any(Long.class))).thenReturn(Optional.of(board));

        FindBoardResponse findBoardResponse = boardService.updateBoard(board.getId(), modifyBoardRequest);

        assertAll(
                () -> assertThat(findBoardResponse.title()).isEqualTo(modifyBoardRequest.title()),
                () -> assertThat(findBoardResponse.content()).isEqualTo(modifyBoardRequest.content())
        );
    }

    @Test
    @DisplayName("게시물 수정에 실패한다.")
    void updateFail() {
        Board board = BoardData.board();
        ReflectionTestUtils.setField(board, "id", 1L);

        ModifyBoardRequest modifyBoardRequest = BoardData.modifyBoardRequest();
        when(boardRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, ()
                -> boardService.updateBoard(board.getId(), modifyBoardRequest));
        assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.Not_EXIST_BOARD);
    }

    @Test
    @DisplayName("id로 게시물을 조회할 수 있다.")
    void findById() {
        Board board = BoardData.board();
        ReflectionTestUtils.setField(board, "id", 1L);
        ReflectionTestUtils.setField(board.getUser(), "id", 1L);

        when(boardRepository.findById(1L)).thenReturn(Optional.of(board));

        FindBoardResponse boardRes = boardService.findById(1L);
        assertAll(
                () -> assertThat(boardRes.content()).isEqualTo(board.getContent()),
                () -> assertThat(boardRes.title()).isEqualTo(board.getTitle()),
                () -> assertThat(boardRes.userId()).isEqualTo(board.getUser().getId())
        );
    }


    @Test
    @DisplayName("모든 게시물을 조회할 수 있다")
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Board> boards = BoardData.getBoards();
        Page<Board> pagedBoards = new PageImpl<>(boards, pageable, boards.size());

        when(boardRepository.findAll(pageable)).thenReturn(pagedBoards);

        PageResponse<FindBoardResponse> response = boardService.findAll(pageable);
        assertEquals(boards.size(), response.getItems().size());
        assertEquals(pagedBoards.getTotalElements(), response.getTotalItems());

        assertEquals(boards.get(0).getTitle(), response.getItems().get(0).title());
        assertEquals(boards.get(0).getContent(), response.getItems().get(0).content());

        assertAll(
                () -> assertEquals(boards.get(0).getTitle(), response.getItems().get(0).title()),
                () -> assertEquals(boards.get(0).getContent(), response.getItems().get(0).content())
        );
    }
}