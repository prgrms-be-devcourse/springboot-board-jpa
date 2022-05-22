package me.prgms.board.domain.post;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Content {

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    public Content() {}

    public Content(String content) {
        validate(content);
        this.content = content;
    }

    private void validate(String content) {
        if (content.isBlank()) {
            throw new IllegalArgumentException("내용을 비울 수 없습니다.");
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        validate(content);
        this.content = content;
    }
}
