package com.spring.board.springboard.post.domain.dto;

public class UpdatePostDTO {
    private String title;
    private String content;
    private Integer memberId;

    public UpdatePostDTO() {
    }

    public UpdatePostDTO(String title, String content, Integer memberId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getMemberId() {
        return memberId;
    }
}
