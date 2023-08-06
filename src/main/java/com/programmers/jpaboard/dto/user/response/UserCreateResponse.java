package com.programmers.jpaboard.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "from")
public class UserCreateResponse {

    private Long userId;
}
