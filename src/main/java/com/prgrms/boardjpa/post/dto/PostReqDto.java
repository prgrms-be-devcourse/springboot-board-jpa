package com.prgrms.boardjpa.post.dto;

import com.prgrms.boardjpa.domain.Post;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static com.prgrms.boardjpa.Utils.now;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDto {
    @NotBlank
    @Length(max = Post.MAX_TITLE_LENGTH)
    private String title;
    @NotBlank
    private String content;
    private Long userId;
}
