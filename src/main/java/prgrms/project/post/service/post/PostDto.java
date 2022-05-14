package prgrms.project.post.service.post;

import lombok.Builder;
import prgrms.project.post.service.user.UserDto;
import prgrms.project.post.util.validation.MySize;

import javax.validation.constraints.NotBlank;

public record PostDto(
        Long id,
        @MySize(max = 100)
        String title,
        @NotBlank
        String content,
        UserDto user
) {

        @Builder
        public PostDto {
        }
}
