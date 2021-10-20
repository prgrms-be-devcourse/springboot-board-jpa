package com.kdt.user.dto;

import com.kdt.post.dto.PostDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;

    @NotNull(message = "Please enter name.")
    @Length(max = 20)
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9-_]{2,19}", message = "Please enter valid name.")
    private String name;

    @NotNull(message = "Please enter age.")
    @Min(value = 1, message = "Please enter age greater than 1.")
    private int age;

    private String hobby;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private List<PostDto> postDtos;
}
