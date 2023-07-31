package kr.co.boardmission.board.domain;

import kr.co.boardmission.member.domain.Member;
import kr.co.boardmission.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class BoardTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    TestEntityManager entityManager;

    @DisplayName("게시글 제목이 비었을 때 예외 던지는지 확인 - ConstraintViolationException")
    @Test
    void board_title_null_ConstraintViolationException() {
        // given
        Member member = memberRepository.save(new Member("member"));
        Board board = BoardFactory.createBoard(null, "content", member);

        // when // then
        assertThatThrownBy(() -> entityManager.persistAndFlush(board))
                .isInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
    }
}
