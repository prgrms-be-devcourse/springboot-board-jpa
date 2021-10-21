package com.example.board.domain.post.dto;

import com.example.board.domain.user.dto.UserDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    private String content;

    @NotNull(message = "작성자는 필수입니다.")
    private UserDto userDto;

    private LocalDateTime createdAt;
}
