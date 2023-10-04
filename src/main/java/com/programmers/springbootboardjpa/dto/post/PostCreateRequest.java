package com.programmers.springbootboardjpa.dto.post;

import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotBlankGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.NotNullGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.PositiveGroup;
import com.programmers.springbootboardjpa.global.validate.ValidationGroups.SizeCheckGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCreateRequest {

    @NotBlank(message = "제목은 Null이거나 공백이거나 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 3, max = 100, message = "제목은 3글자 이상, 100글자 이하여야합니다.", groups = SizeCheckGroup.class)
    private String title;

    @NotBlank(message = "내용은 Null이거나 공백이거나 값이 없을 수 없습니다.", groups = NotBlankGroup.class)
    @Size(min = 3, message = "내용은 3글자 이상이어야합니다.", groups = SizeCheckGroup.class)
    private String content;

    @NotNull(message = "사용자의 id값은 Null일 수 없습니다.", groups = NotNullGroup.class)
    @Positive(message = "사용자의 id값은 숫자여야하고 양수여야합니다.", groups = PositiveGroup.class)
    private Long userId;

    @Builder
    public PostCreateRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

}
