package kdt.prgrms.devrun.post.dto;

import kdt.prgrms.devrun.domain.Post;
import kdt.prgrms.devrun.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPostRequestDto {

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    @Length(max = 200, message = "200자 이하로 적어주세요.")
    private String title;

    @Length(max = 1000, message = "1000자 이하로 적어주세요.")
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
