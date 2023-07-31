package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.domain.BoardRepository;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public String createBoard(BoardCreateRequest request) {
        final Board board = boardMapper.toBoard(request);
        boardRepository.save(board);
        return board.getCreatedBy();
    }
}
