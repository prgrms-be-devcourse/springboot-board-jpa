package me.prgms.board.domain.post;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Title {

    private static final int TITLE_MAX_LENGTH = 50;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    public Title() {}

    public Title(String title) {
        validate(title);
        this.title = title;
    }

    private void validate(String title) {
        if (title.isBlank()) {
            throw new IllegalArgumentException("제목을 비울 수 없습니다.");
        }

        if (title.length() > TITLE_MAX_LENGTH) {
            throw new IllegalArgumentException("제목은 50글자를 넘길 수 없습니다.");
        }
    }

    public String getTitle() {
        return title;
    }

    public void changeTitle(String title) {
        validate(title);
        this.title = title;
    }
}
