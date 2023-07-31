package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardFactory;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.member.application.MemberService;
import kr.co.boardmission.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {
    @InjectMocks
    private BoardService boardService;

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private MemberService memberService;

    @Spy
    private BoardMapper boardMapper;

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create_board_success() {
        // given
        BoardCreateRequest createRequest = BoardFactory.createBoardCreateRequest();
        Member member = new Member("memberName");
        Board board = boardMapper.toBoard(createRequest, member);

        given(memberService.getMember(any(Long.class))).willReturn(member);
        given(boardRepository.save(any(Board.class))).willReturn(board);

        // when
        String actual = boardService.createBoard(createRequest);

        // then
        assertThat(actual).isEqualTo(board.getCreatedBy());
    }
}
