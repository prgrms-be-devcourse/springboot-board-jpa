package kr.co.boardmission.board.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class BoardTest {
    @Autowired
    TestEntityManager entityManager;

    @DisplayName("게시글 제목이 비었을 때 예외 던지는지 확인 - ConstraintViolationException")
    @Test
    void board_title_null_ConstraintViolationException() {
        // given
        Board board = BoardFactory.createBoard(null, "content");

        // when // then
        assertThatThrownBy(() -> entityManager.persistAndFlush(board))
                .isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
    }
}
