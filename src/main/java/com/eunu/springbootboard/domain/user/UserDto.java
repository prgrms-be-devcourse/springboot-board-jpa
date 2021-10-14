package com.eunu.springbootboard.domain.user;

import com.eunu.springbootboard.domain.post.PostDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String id;
    private String name;
    private int age;
    private String hobby;

    private List<PostDto> postDtos;

    private String createdBy;
    private LocalDateTime createdAt;
}
