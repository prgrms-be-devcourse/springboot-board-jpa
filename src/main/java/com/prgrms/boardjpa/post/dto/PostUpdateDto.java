package com.prgrms.boardjpa.post.dto;

import com.prgrms.boardjpa.domain.Post;
import lombok.*;

import static com.prgrms.boardjpa.Utils.now;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostUpdateDto {
    private Long id;
    private String title;
    private String content;
}
