package kdt.prgrms.devrun.post.dto;

import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditPostRequestDto {

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    private String title;

    private String content;

    @NotBlank(message = "작성자 ID는 필수 입력 사항입니다.")
    private String createdBy;

    public Post convertToEntity(User user) {
        return Post.builder()
            .user(user)
            .title(title)
            .content(content)
            .build();
    }

}
