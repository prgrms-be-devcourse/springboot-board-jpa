package com.poogle.board.controller.post;

import com.poogle.board.controller.user.UserResponse;
import com.poogle.board.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private String modifiedBy;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserResponse userResponse;

    public PostResponse(Post source) {
        copyProperties(source, this);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("title", title)
                .append("content", content)
                .append("createdBy", createdBy)
                .append("modifiedBy", modifiedBy)
                .append("createdAt", createdAt)
                .append("modifiedAt", modifiedAt)
                .toString();
    }
}
