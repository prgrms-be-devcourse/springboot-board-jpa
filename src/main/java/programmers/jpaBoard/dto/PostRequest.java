package programmers.jpaBoard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import programmers.jpaBoard.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class PostRequest {

    private String title;

    private String content;

    private User user;

    public PostRequest(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
