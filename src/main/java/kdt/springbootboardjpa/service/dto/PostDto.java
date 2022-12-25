package kdt.springbootboardjpa.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Long createdBy;

    @Builder
    public PostDto(Long id, String title, String content, Long createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }
}
