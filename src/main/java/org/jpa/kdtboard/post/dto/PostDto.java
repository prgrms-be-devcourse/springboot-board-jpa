package org.jpa.kdtboard.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.jpa.kdtboard.domain.board.HomeworkStatus;
import org.jpa.kdtboard.domain.board.PostScope;
import org.jpa.kdtboard.domain.board.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/10/12.
 */

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id ;
    private String title;
    private String content;
    private String password;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PostType postType;

    // Homework post
    private HomeworkStatus homeworkStatus = HomeworkStatus.NONE;

    // Notice post
    private LocalDateTime expireDate = LocalDateTime.of(1970, 01, 01, 00, 00, 00);

    // Question post
    private PostScope postScope = PostScope.NONE;

}
