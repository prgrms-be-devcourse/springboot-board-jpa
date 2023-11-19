package com.kdt.simpleboard.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@Getter
public class BoardRequest {
    public record CreateBoardRequest(
            Long memberId,

            @NotBlank(message = "제목을 입력해주세요")
            String title,

            @NotBlank(message = "내용을 입력해주세요")
            String content
    ){}
    public record ModifyBoard(
            @NotBlank(message = "제목을 입력해주세요")
            String title,

            @NotBlank(message = "내용을 입력해주세요")
            String content
    ){}
}
