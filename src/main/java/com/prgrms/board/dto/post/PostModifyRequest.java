package com.prgrms.board.dto.post;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class PostModifyRequest {

    @Positive
    private Long id;

    @NotBlank(message = "제목이 비어있습니다.")
    private String title;

    private String content;

    public PostModifyRequest(long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
