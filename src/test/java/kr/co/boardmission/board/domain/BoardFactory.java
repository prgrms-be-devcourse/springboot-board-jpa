package kr.co.boardmission.board.domain;

import kr.co.boardmission.board.dto.request.BoardRequest;
import kr.co.boardmission.member.domain.Member;

public class BoardFactory {
    public static Board createBoard(String title, String content, Member member) {
        return Board.create(title, content, member);
    }

    public static BoardRequest createBoardCreateRequest(Long memberId) {
        return new BoardRequest("title", "content", memberId);
    }
}
