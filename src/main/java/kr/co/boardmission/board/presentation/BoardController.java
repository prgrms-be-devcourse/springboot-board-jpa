package kr.co.boardmission.board.presentation;

import jakarta.validation.Valid;
import kr.co.boardmission.board.application.BoardService;
import kr.co.boardmission.board.dto.request.BoardRequest;
import kr.co.boardmission.board.dto.response.BoardResponse;
import kr.co.boardmission.board.dto.response.BoardSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody @Valid BoardRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(boardService.createBoard(request));
    }

    @GetMapping("/{board_id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable(name = "board_id") Long boardId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.getBoard(boardId));
    }

    @GetMapping
    public ResponseEntity<List<BoardSummary>> getBoardsPage(
            @PageableDefault(size = 2, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.findAll(pageable));
    }

    @PutMapping("/{board_id}")
    public ResponseEntity<String> getBoardsPage(
            @PathVariable(name = "board_id") Long boardId,
            @RequestBody @Valid BoardRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(boardService.updateBoard(boardId, request));
    }
}
