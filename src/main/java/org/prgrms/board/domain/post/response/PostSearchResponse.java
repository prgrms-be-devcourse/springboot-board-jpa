package org.prgrms.board.domain.post.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.prgrms.board.domain.user.response.UserSearchResponse;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class PostSearchResponse {
    private long postId;
    private String title;
    private String content;
    private UserSearchResponse user;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
