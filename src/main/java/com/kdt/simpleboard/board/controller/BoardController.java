package com.kdt.simpleboard.board.controller;

import com.kdt.simpleboard.board.service.BoardService;
import com.kdt.simpleboard.common.dto.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kdt.simpleboard.board.dto.BoardRequest.CreateBoardRequest;
import static com.kdt.simpleboard.board.dto.BoardRequest.ModifyBoard;
import static com.kdt.simpleboard.board.dto.BoardResponse.CreateBoardRes;
import static com.kdt.simpleboard.board.dto.BoardResponse.FindBoardRes;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @Operation(summary = "보드 생성")
    @PostMapping
    public ResponseEntity<CreateBoardRes> createBoard(@Valid @RequestBody CreateBoardRequest request){
        CreateBoardRes response = boardService.createBoard(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "보드 업데이트")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 생성 성공",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/{id}")
    public ResponseEntity<FindBoardRes> updateBoard(@PathVariable("id") Long boardId, @Valid @RequestBody ModifyBoard request) {
        FindBoardRes response = boardService.updateBoard(boardId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "보드 단건 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 생성 성공",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{id}")
    public ResponseEntity<FindBoardRes> findBoard(@PathVariable("id") Long boardId){
        FindBoardRes response = boardService.findById(boardId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "보드 모두 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 생성 성공",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<PageResponse<FindBoardRes>> findAll(Pageable pageable){
        PageResponse<FindBoardRes> response = boardService.findAll(pageable);
        return ResponseEntity.ok(response);
    }
}
