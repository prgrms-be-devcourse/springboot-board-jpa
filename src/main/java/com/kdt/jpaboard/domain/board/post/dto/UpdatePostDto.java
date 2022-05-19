package com.kdt.jpaboard.domain.board.post.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePostDto {
    private String title;
    private String content;

}
