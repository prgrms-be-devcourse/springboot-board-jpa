package org.programmers.board.controller;

import org.programmers.board.dto.BoardCreateRequest;
import org.programmers.board.dto.BoardResponse;
import org.programmers.board.dto.BoardUpdateRequest;
import org.programmers.board.dto.UserDTO;
import org.programmers.board.entity.Board;
import org.programmers.board.entity.vo.Content;
import org.programmers.board.entity.vo.Title;
import org.programmers.board.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class BoardApiController {

    private final BoardService boardService;

    public BoardApiController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 페이징 조회
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getPages() {
        List<BoardResponse> collect = boardService.getBoards().stream()
                .map((board -> new BoardResponse(board.getId(),
                        board.getTitle().getTitle(),
                        board.getContent().getContent(),
                        board.getUser().getName().getName()))).collect(Collectors.toList());

        return ResponseEntity.ok(collect);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        Board board = boardService.getBoard(id);

        BoardResponse boardResponse = new BoardResponse(board.getId(),
                board.getTitle().getTitle(),
                board.getContent().getContent(),
                board.getUser().getName().getName());

        return ResponseEntity.ok(boardResponse);
    }

    // POST : 게시글 작성
    @PostMapping
    public ResponseEntity<BoardResponse> create(@RequestBody BoardCreateRequest boardCreateRequest) {
        Board newBoard = new Board(
                new Title(boardCreateRequest.getTitle()),
                new Content(boardCreateRequest.getContent()),
                UserDTO.toEntity(boardCreateRequest.getUserDTO()));

        Long createdBoardId = boardService.createBoard(newBoard);
        Board board = boardService.getBoard(createdBoardId);

        BoardResponse boardResponse = new BoardResponse(board.getId(),
                board.getTitle().getTitle(),
                board.getContent().getContent(),
                board.getUser().getName().getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(boardResponse);
    }

    // POST : 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(
            @RequestBody BoardUpdateRequest boardUpdateRequest,
            @PathVariable Long id) {
        Board updatedBoard = boardService.updateBoard(id, boardUpdateRequest);

        BoardResponse boardResponse = new BoardResponse(
                updatedBoard.getId(),
                updatedBoard.getTitle().getTitle(),
                updatedBoard.getContent().getContent(),
                updatedBoard.getUser().getName().getName()
        );

        return ResponseEntity.ok(boardResponse);
    }
}