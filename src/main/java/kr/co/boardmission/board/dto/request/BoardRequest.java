package kr.co.boardmission.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardRequest {
    @NotBlank
    private String title;

    private String content;

    @NotNull
    private Long memberId;

    public BoardRequest(
            String title,
            String content,
            Long memberId
    ) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }
}
