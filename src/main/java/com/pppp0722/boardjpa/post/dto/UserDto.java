package com.pppp0722.boardjpa.post.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private LocalDateTime createdAt;
    private String createdBy;
    private Long id;
    private String name;
    private Integer age;
    private String hobby;
    private List<PostDto> postDtos;
}
