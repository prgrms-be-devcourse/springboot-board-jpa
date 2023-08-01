package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardRequest;
import kr.co.boardmission.board.dto.response.BoardResponse;
import kr.co.boardmission.board.dto.response.BoardSummary;
import kr.co.boardmission.member.application.MemberService;
import kr.co.boardmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final MemberService memberService;

    @Transactional
    public String createBoard(BoardRequest request) {
        Member member = memberService.getMember(request.getMemberId());
        Board board = boardMapper.toBoard(request, member);
        Board savedBoard = boardRepository.save(board);

        return savedBoard.getCreatedBy();
    }

    public BoardResponse getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Not Found Board"));

        return boardMapper.toDto(board);
    }

    public List<BoardSummary> findAll(Pageable pageable) {
        return boardRepository.findAll(pageable)
                .map(BoardMapper::toSummaryDto)
                .getContent();
    }

    @Transactional
    public String updateBoard(
            Long boardId,
            BoardRequest request
    ) {
        Member member = memberService.getMember(request.getMemberId());
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Not Found Board"));

        board.updateBoard(request, member.getName());

        return board.getCreatedBy();
    }
}
