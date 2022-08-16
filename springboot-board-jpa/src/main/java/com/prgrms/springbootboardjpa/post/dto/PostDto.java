package com.prgrms.springbootboardjpa.post.dto;

import com.prgrms.springbootboardjpa.Patterns;
import com.sun.istack.NotNull;
import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;

    @NotNull
    @Size(min=1, max = 200)
    private String title;

    @NotNull
    private String content;


    @NotNull
    @Pattern(regexp = Patterns.PASSWD_PATTERN)
    @Size(min = 8, max = 100)
    private String password;


    @NotNull
    @Pattern(regexp = Patterns.EMAIL_PATTERN)
    @Size(min = 5, max = 50)
    private String email;
}
