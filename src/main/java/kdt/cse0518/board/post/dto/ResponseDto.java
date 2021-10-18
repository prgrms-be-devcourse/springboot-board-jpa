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
    private Long postId;
    private String title;
    private String content;

    private ResponseDto() {
    }
}
