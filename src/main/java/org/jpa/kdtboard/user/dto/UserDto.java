package org.jpa.kdtboard.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by yunyun on 2021/10/14.
 */

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private String hobby;
    private String createdBy;
}
