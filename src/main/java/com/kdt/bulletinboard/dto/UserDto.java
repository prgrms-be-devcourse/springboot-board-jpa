package com.kdt.bulletinboard.dto;

import com.kdt.bulletinboard.entity.Hobby;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Hobby hobby;
}
