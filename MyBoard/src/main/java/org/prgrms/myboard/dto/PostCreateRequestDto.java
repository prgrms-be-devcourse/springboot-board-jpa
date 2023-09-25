package org.prgrms.myboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.prgrms.myboard.domain.Post;
import org.prgrms.myboard.domain.User;

import static org.prgrms.myboard.util.ErrorMessage.*;

public record PostCreateRequestDto(
    @NotBlank(message = TITLE_NOT_BLANK_MESSAGE)
    String title,
    @NotBlank(message = CONTENT_NOT_BLANK_MESSAGE)
    String content,
    @NotNull(message = ID_NOT_NULL_MESSAGE)
    Long userId
) {
    public Post toPost(User user) {
        return new Post(title(), content(), user);
    }
}
