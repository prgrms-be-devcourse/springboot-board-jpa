package org.programmers.board.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.entity.Board;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Content;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.entity.vo.Title;
import org.programmers.board.exception.EmptyContentException;
import org.programmers.board.exception.EmptyTitleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardRepositoryTest {

    User user;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUser() {
        user = new User(new Name("wisehero"), 27, "coding");
        userRepository.save(user);
    }

    @Test
    @DisplayName("게시글 저장 성공")
    void saveBoard() {
        Board board = new Board(new Title("이것은 제목"), new Content("이것은 내용"), user);

        Board savedBoard = boardRepository.save(board);


        assertAll(
                () -> assertThat(savedBoard.getId()).isEqualTo(board.getId()),
                () -> assertThat(savedBoard.getContent().getContent()).isEqualTo(board.getContent().getContent()),
                () -> assertThat(savedBoard.getTitle().getTitle()).isEqualTo(board.getTitle().getTitle()),
                () -> assertThat(savedBoard.getUser()).isSameAs(user)
        );
    }

    @Test
    @DisplayName("게시글 저장 실패 - 사유: 빈 제목")
    void saveBoardFailedByEmptyTitle() {

        assertThatThrownBy(
                () -> boardRepository.
                        save(new Board(new Title(""), new Content("이것은 내용"), user)))
                .isInstanceOf(EmptyTitleException.class)
                .hasMessageContaining("제목을 입력해주세요.");
    }

    @Test
    @DisplayName("게시글 저장 성공 - 사유: 내용 없음")
    void saveBoardFailedByEmptyContent() {

        assertThatThrownBy(
                () -> boardRepository.
                        save(new Board(new Title("이것은 제목"), new Content(""), user)))
                .isInstanceOf(EmptyContentException.class)
                .hasMessageContaining("내용을 입력해주세요.");
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void findAllBoard() {

        Board board1 = new Board(new Title("이것은 제목"), new Content("이것은 내용"), user);
        Board board2 = new Board(new Title("이것은 제목"), new Content("이것은 내용"), user);
        Board board3 = new Board(new Title("이것은 제목"), new Content("이것은 내용"), user);
        boardRepository.saveAll(List.of(board1, board2, board3));

        List<Board> all = boardRepository.findAll();

        assertThat(all).hasSize(3);
    }

    @Test
    @DisplayName("게시글 ID로 조회")
    void findBoardById() {
        Board board1 = new Board(new Title("이것은 제목"), new Content("이것은 내용"), user);
        boardRepository.save(board1);

        Optional<Board> findBoard = boardRepository.findById(board1.getId());

        assertThat(findBoard.isPresent()).isTrue();
    }

    @Test
    @DisplayName("게시글 삭제")
    void deleteBoard() {
        Board board = new Board(new Title("이것은 제목"), new Content("이것은 내용"), user);
        Board savedBoard = boardRepository.save(board);

        boardRepository.deleteById(savedBoard.getId());

        Optional<Board> findBoard = boardRepository.findById(board.getId());

        assertThat(findBoard.isPresent()).isFalse();
    }

}