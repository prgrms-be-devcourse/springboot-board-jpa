package com.programmers.jpaboard.board.controller;

import com.programmers.jpaboard.board.controller.dto.BoardCreationDto;
import com.programmers.jpaboard.board.controller.dto.BoardResponseDto;
import com.programmers.jpaboard.board.controller.dto.BoardUpdateDto;
import com.programmers.jpaboard.board.converter.BoardConverter;
import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.service.BoardService;
import com.programmers.jpaboard.member.ApiResponse;
import com.programmers.jpaboard.member.domain.Member;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BoardController {

    private final BoardService boardService;
    private final BoardConverter boardConverter;

    public BoardController(BoardService boardService, BoardConverter boardConverter) {
        this.boardService = boardService;
        this.boardConverter = boardConverter;
    }

    @PostMapping("/boards")
    public ApiResponse<BoardResponseDto> createBoard(@Valid @RequestBody BoardCreationDto boardCreationDto/*, Member member*/) {
        Member member = Member.builder()
                .name("name")
                .age(10)
                .hobbies(List.of("hobby"))
                .build();
        Board board = boardConverter.convertBoardByCreation(boardCreationDto);
        Board saved = boardService.saveBoard(board, member);

        BoardResponseDto responseDto = boardConverter.convertBoardResponseDto(saved);
        return ApiResponse.ok("Success Board Creation", responseDto);
    }

    @GetMapping("/boards")
    public ApiResponse<List<BoardResponseDto>> lookupAllBoard(){
        List<Board> boards = boardService.findAll();
        List<BoardResponseDto> result = boards.stream()
                .map(boardConverter::convertBoardResponseDto)
                .collect(Collectors.toList());

        return ApiResponse.ok("Success All Board Lookup", result);
    }

    @GetMapping("/boards/{boardId}")
    public ApiResponse<BoardResponseDto> lookupBoard(@PathVariable Long boardId){
        Board board = boardService.findOne(boardId);

        BoardResponseDto responseDto = boardConverter.convertBoardResponseDto(board);
        return ApiResponse.ok("Success Board Lookup", responseDto);
    }


    @PostMapping("/boards/{boardId}")
    public ApiResponse<BoardResponseDto> updateBoard(@Validated @ModelAttribute BoardUpdateDto boardUpdateDto, @PathVariable Long boardId) {
        Board board = boardConverter.convertBoardByUpdate(boardUpdateDto);

        Board updatedBoard = boardService.updateBoard(boardId, board);
        BoardResponseDto responseDto = boardConverter.convertBoardResponseDto(updatedBoard);

        return ApiResponse.ok("Success Board Update", responseDto);
    }
}
