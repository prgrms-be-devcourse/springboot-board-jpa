package kr.co.boardmission.board.domain;

import kr.co.boardmission.board.dto.request.BoardCreateRequest;

public class BoardFactory {
    public static Board createBoard(BoardCreateRequest boardCreateRequest) {
        return Board.create(boardCreateRequest.getTitle(), boardCreateRequest.getContent(), boardCreateRequest.getCreatedBy());
    }

    public static Board createBoard(String title, String content) {
        return Board.create(title, "content", "memberName");
    }

    public static BoardCreateRequest createBoardCreateRequest() {
        return new BoardCreateRequest("title", "content", "memberName");
    }
}
