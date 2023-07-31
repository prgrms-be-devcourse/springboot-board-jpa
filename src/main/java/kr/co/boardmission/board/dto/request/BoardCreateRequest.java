package kr.co.boardmission.board.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardCreateRequest {
    @NotBlank
    private String title;

    private String content;

    private String createdBy;

    public BoardCreateRequest(
            String title,
            String content,
            String createdBy
    ) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }
}
