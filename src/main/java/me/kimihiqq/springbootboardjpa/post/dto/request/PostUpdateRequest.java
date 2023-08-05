package me.kimihiqq.springbootboardjpa.post.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostUpdateRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;
}

