package org.prgrms.board.domain.post.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class PostUpdateRequest {
    @NotBlank
    @Size(max = 50)
    private String title;
    @NotBlank
    private String content;
}
