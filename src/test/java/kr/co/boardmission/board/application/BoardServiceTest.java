package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardFactory;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.board.dto.response.BoardResponse;
import kr.co.boardmission.member.application.MemberService;
import kr.co.boardmission.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("memberName");
    }

    @DisplayName("게시글 생성 - 성공")
    @Test
    void create_board_success() {
        // given
        BoardCreateRequest createRequest = BoardFactory.createBoardCreateRequest();
        Board board = boardMapper.toBoard(createRequest, member);

        given(memberService.getMember(any(Long.class))).willReturn(member);
        given(boardRepository.save(any(Board.class))).willReturn(board);

        // when
        String actual = boardService.createBoard(createRequest);

        // then
        assertThat(actual).isEqualTo(board.getCreatedBy());
    }

    @DisplayName("게시글 단건 조회 - 성공")
    @Test
    void get_boards_detail_success() {
        // given
        Board board = BoardFactory.createBoard("title", "content", member);
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));

        // when
        BoardResponse actual = boardService.getBoard(1L);

        // then
        assertThat(actual.title()).isEqualTo(board.getTitle());
        assertThat(actual.content()).isEqualTo(board.getContent());
        assertThat(actual.createdAt()).isEqualTo(board.getCreatedAt());
        assertThat(actual.modifiedAt()).isEqualTo(board.getModifiedAt());
        assertThat(actual.createdBy()).isEqualTo(board.getCreatedBy());
        assertThat(actual.modifiedBy()).isEqualTo(board.getModifiedBy());
    }
}
