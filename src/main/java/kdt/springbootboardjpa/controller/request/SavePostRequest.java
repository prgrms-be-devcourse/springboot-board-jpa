package kdt.springbootboardjpa.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SavePostRequest {
    private String title;
    private String content;
    private Long createdBy;

    @Builder
    public SavePostRequest(String title, String content, Long createdBy) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }
}
