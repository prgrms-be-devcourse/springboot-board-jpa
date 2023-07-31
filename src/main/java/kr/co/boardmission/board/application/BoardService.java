package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.member.application.MemberService;
import kr.co.boardmission.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final MemberService memberService;

    public String createBoard(BoardCreateRequest request) {
        final Member member = memberService.getMember(request.getMemberId());
        final Board board = boardMapper.toBoard(request, member);
        final Board savedBoard = boardRepository.save(board);

        return savedBoard.getCreatedBy();
    }
}
