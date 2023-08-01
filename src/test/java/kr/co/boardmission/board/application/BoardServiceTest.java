package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardFactory;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardRequest;
import kr.co.boardmission.board.dto.response.BoardResponse;
import kr.co.boardmission.board.dto.response.BoardSummary;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
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
        // Given
        BoardRequest createRequest = BoardFactory.createBoardCreateRequest(1L);
        Board board = boardMapper.toBoard(createRequest, member);

        given(memberService.getMember(any(Long.class))).willReturn(member);
        given(boardRepository.save(any(Board.class))).willReturn(board);

        // When
        String actual = boardService.createBoard(createRequest);

        // Then
        assertThat(actual).isEqualTo(board.getCreatedBy());
    }

    @DisplayName("게시글 단건 조회 - 성공")
    @Test
    void get_boards_detail_success() {
        // Given
        Board board = BoardFactory.createBoard("title", "content", member);
        given(boardRepository.findById(1L)).willReturn(Optional.of(board));

        // When
        BoardResponse actual = boardService.getBoard(1L);

        // Then
        assertThat(actual.title()).isEqualTo(board.getTitle());
        assertThat(actual.content()).isEqualTo(board.getContent());
        assertThat(actual.createdAt()).isEqualTo(board.getCreatedAt());
        assertThat(actual.modifiedAt()).isEqualTo(board.getModifiedAt());
        assertThat(actual.createdBy()).isEqualTo(board.getCreatedBy());
        assertThat(actual.modifiedBy()).isEqualTo(board.getModifiedBy());
    }

    @DisplayName("게시글 페이지 단위 조회 - 성공")
    @Test
    void get_boards_paging_success() {
        // Given
        Board board1 = BoardFactory.createBoard("t1", "c1", member);
        Board board2 = BoardFactory.createBoard("t2", "c2", member);
        Page<Board> boards = new PageImpl<>(List.of(board1, board2));

        given(boardRepository.findAll(any(Pageable.class))).willReturn(boards);

        // When
        List<BoardSummary> actual = boardService.findAll(PageRequest.of(0, 2));

        // Then
        assertThat(actual).hasSize(2);
    }

    @DisplayName("게시판 페이지 단위 수정 - 성공")
    @Test
    void update_board_success() {
        // Given
        Board board = BoardFactory.createBoard("t1", "c1", member);
        BoardRequest request = BoardFactory.createBoardCreateRequest(1L);

        given(memberService.getMember(any(Long.class))).willReturn(member);
        given(boardRepository.findById(any(Long.class))).willReturn(Optional.of(board));

        // When
        String actual = boardService.updateBoard(1L, request);

        // Then
        assertThat(actual).isEqualTo(member.getName());
    }
}
