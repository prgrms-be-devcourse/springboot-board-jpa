package kr.co.boardmission.board.presentation;

import jakarta.validation.Valid;
import kr.co.boardmission.board.application.BoardService;
import kr.co.boardmission.board.dto.request.BoardCreateRequest;
import kr.co.boardmission.board.dto.response.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> createBoard(@RequestBody @Valid BoardCreateRequest request) {
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
}
