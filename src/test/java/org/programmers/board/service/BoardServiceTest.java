package org.programmers.board.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.dto.BoardUpdateRequest;
import org.programmers.board.entity.Board;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Content;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.entity.vo.Title;
import org.programmers.board.repository.BoardRepository;
import org.programmers.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BoardService boardService;

    Board board;
    User user;

    @BeforeEach
    void setup() {
        user = new User(new Name("김지웅"), 27, "독서");
        board = new Board(new Title("this is title"), new Content("this is content"), user);
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 생성")
    @Transactional
    void createBoardTest() {
        Long createdBoardId = boardService.createBoard(board);

        Board findBoard = boardRepository.findById(createdBoardId).get();

        assertAll(
                () -> assertThat(findBoard.getUser().getName().getName()).isEqualTo(user.getName().getName()),
                () -> assertThat(findBoard.getTitle().getTitle()).isEqualTo("this is title"),
                () -> assertThat(findBoard.getContent().getContent()).isEqualTo("this is content"),
                () -> assertThat(findBoard.getId()).isEqualTo(board.getId())
        );
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void getBoardTest() {
        Board savedBoard = boardRepository.save(board);

        Board findBoard = boardService.getBoard(savedBoard.getId());

        assertAll(
                () -> assertThat(findBoard.getId()).isEqualTo(savedBoard.getId()),
                () -> assertThat(findBoard.getContent().getContent()).isEqualTo(savedBoard.getContent().getContent()),
                () -> assertThat(findBoard.getTitle().getTitle()).isEqualTo(savedBoard.getTitle().getTitle())
        );
    }

    @Test
    @DisplayName("게시글 제목 업데이트")
    void updateTitleTest() {
        Board saveBoard = boardRepository.save(board);
        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest("새로운 제목", board.getContent().getContent());

        Long updateBoardId = boardService.updateBoard(saveBoard.getId(), boardUpdateRequest);

        Board updatedBoard = boardRepository.findById(updateBoardId).get();
        assertAll(
                () -> assertThat(saveBoard.getContent().getContent()).isEqualTo(updatedBoard.getContent().getContent()),
                () -> assertThat(updatedBoard.getTitle().getTitle()).isNotEqualTo(saveBoard.getTitle().getTitle())
        );
    }

    @Test
    @DisplayName("게시글 내용 업데이트")
    void updateContent() {
        Board saveBoard = boardRepository.save(board);
        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest(board.getTitle().getTitle(), "새로운 내용");

        Long updateBoardId = boardService.updateBoard(saveBoard.getId(), boardUpdateRequest);

        Board updatedBoard = boardRepository.findById(updateBoardId).get();
        assertAll(
                () -> assertThat(saveBoard.getContent().getContent()).isNotEqualTo(updatedBoard.getContent().getContent()),
                () -> assertThat(updatedBoard.getTitle().getTitle()).isEqualTo(saveBoard.getTitle().getTitle())
        );
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void getBoardsTest() {
        boardRepository.save(board);

        List<Board> boards = boardService.getBoards();

        assertThat(boards.size()).isEqualTo(1);
    }
}