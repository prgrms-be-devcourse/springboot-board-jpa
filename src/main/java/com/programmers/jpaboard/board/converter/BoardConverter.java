package com.programmers.jpaboard.board.converter;

import com.programmers.jpaboard.board.controller.dto.BoardCreationDto;
import com.programmers.jpaboard.board.controller.dto.BoardResponseDto;
import com.programmers.jpaboard.board.controller.dto.BoardUpdateDto;
import com.programmers.jpaboard.board.domian.Board;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter {

    public Board convertBoardByCreation(BoardCreationDto boardCreationDto) {
        return Board.builder()
                .title(boardCreationDto.getTitle())
                .content(boardCreationDto.getContent())
                .build();
    }

    public BoardResponseDto convertBoardResponseDto(Board board) {
        return BoardResponseDto.builder()
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
