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
public class PostForm {

    @NotBlank
    private String createdBy;

    @NotBlank
    private String title;

    private String content;

    public Post convertToEntity(User user) {
        return new Post(title, content, user);
    }

}
