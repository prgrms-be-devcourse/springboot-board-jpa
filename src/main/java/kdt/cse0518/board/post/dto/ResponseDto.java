package kdt.cse0518.board.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private String title;
    private String content;
    private Long userId;

    private ResponseDto() {
    }
}
