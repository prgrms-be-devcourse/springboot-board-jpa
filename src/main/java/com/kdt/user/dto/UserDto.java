package com.kdt.user.dto;

import com.kdt.post.dto.PostDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private String name;
    private int age;
    private String hobby;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
    private List<PostDto> postDtos;
}
