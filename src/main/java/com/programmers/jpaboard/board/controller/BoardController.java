package com.programmers.jpaboard.board.controller;

import com.programmers.jpaboard.board.controller.converter.BoardConverter;
import com.programmers.jpaboard.board.controller.dto.BoardCreationDto;
import com.programmers.jpaboard.board.controller.dto.BoardResponseDto;
import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.service.BoardService;
import com.programmers.jpaboard.member.ApiResponse;
import com.programmers.jpaboard.member.domain.Member;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardConverter boardConverter;

    public BoardController(BoardService boardService, BoardConverter boardConverter) {
        this.boardService = boardService;
        this.boardConverter = boardConverter;
    }

    @PostMapping("/boards")
    public ApiResponse<BoardResponseDto> createBoard(BoardCreationDto boardCreationDto, Member member) {
        Board board = boardConverter.convertBoard(boardCreationDto);
        Board saved = boardService.saveBoard(board, member);

        return ApiResponse.ok("Success Board Creation", boardConverter.convertBoardResponseDto(saved));
    }
}
