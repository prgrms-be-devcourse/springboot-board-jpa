package me.prgms.board.post.dto;

public class UpdatePostDto {
    private String title;
    private String content;

    public UpdatePostDto() {}

    public UpdatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
