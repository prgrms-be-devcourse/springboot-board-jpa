package com.assignment.bulletinboard.post.dto;

import com.assignment.bulletinboard.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PostUpdateDto {

        private Long id;

        @NotBlank(message = "변경할 제목을 입력해주세요.")
        @Length(max = 30, message = "제목은 최대 30자를 넘을 수 없습니다.")
        private String title;

        @NotBlank(message = "변경할 내용을 입력해주세요.")
        private String content;

        private UserDto userDto;
}
