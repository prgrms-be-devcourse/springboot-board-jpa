package com.kdt.post.dto;

import com.kdt.user.dto.UserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long id;

    @NotNull
    @Length(max = 100)
    private String title;

    @NotNull
    @Length(max = 1000)
    private String content;

    private UserDto userDto;

    private String createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime lastUpdatedAt;
}
