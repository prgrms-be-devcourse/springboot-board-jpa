package com.prgrms.boardjpa.post.dto;

import com.prgrms.boardjpa.user.dto.AuthorDto;
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
    private AuthorDto author;
    private LocalDateTime createdAt;
}
