package kr.co.boardmission.board.application;

import kr.co.boardmission.board.domain.Board;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.board.dto.response.BoardResponse;
import kr.co.boardmission.member.domain.Member;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public Board toBoard(BoardCreateRequest request, Member member) {
        return Board.create(request.getTitle(), request.getContent(), member);
    }

    public BoardResponse toDto(Board board) {
        return BoardResponse.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .createdBy(board.getCreatedBy())
                .modifiedBy(board.getModifiedBy())
                .build();
    }
}
