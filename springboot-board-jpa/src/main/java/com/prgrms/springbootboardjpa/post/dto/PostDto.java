package com.prgrms.springbootboardjpa.post.dto;

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
    private final String PASSWD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private final String EMAIL_PATTERN = "\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b";

    private Long id;

    @NotNull
    @Size(min=1, max = 200)
    private String title;

    @NotNull
    private String content;


    @NotNull
    @Pattern(regexp = PASSWD_PATTERN)
    @Size(min = 8, max = 100)
    private String password;


    @NotNull
    @Pattern(regexp = EMAIL_PATTERN)
    @Size(min = 5, max = 50)
    private String email;
}
