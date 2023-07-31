package kr.co.boardmission.board.domain;

import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.member.domain.Member;

public class BoardFactory {
    public static Board createBoard(String title, String content, Member member) {
        return Board.create(title, content, member);
    }

    public static BoardCreateRequest createBoardCreateRequest() {
        return new BoardCreateRequest("title", "content", 1L);
    }
}
