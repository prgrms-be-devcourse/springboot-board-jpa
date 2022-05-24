package com.prgrms.boardjpa.post.dto;

import com.prgrms.boardjpa.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResDto {
    private Long id;
    private String title;
    private String content;
    private User author;
    private LocalDateTime createdAt;
    private String createdBy;
}
