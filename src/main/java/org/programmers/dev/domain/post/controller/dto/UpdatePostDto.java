package org.programmers.dev.domain.post.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UpdatePostDto {

    private String title;
    private String content;

    @Builder
    private UpdatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
