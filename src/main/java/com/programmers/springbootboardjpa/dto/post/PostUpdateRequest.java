package com.programmers.springbootboardjpa.dto.post;

import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.SizeCheckGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateRequest {

    @NotBlank(message = "제목은 Null이거나 공백이거나 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 3, max = 100, message = "제목은 3글자 이상, 100글자 이하여야합니다.", groups = SizeCheckGroup.class)
    private String title;

    @NotBlank(message = "내용은 Null이거나 공백이거나 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 3, message = "내용은 3글자 이상이어야합니다.", groups = SizeCheckGroup.class)
    private String content;

    @Builder
    public PostUpdateRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
