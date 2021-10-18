package com.kdt.post.dto;

import com.kdt.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {
    private Long id;
    private String title;
    private String conent;
    private UserDto userDto;
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdatedAt;
}
