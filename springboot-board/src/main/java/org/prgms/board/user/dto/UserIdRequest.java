package org.prgms.board.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserIdRequest {
    @NotBlank
    private Long userId;

    public UserIdRequest() {
    }

    public UserIdRequest(Long userId) {
        this.userId = userId;
    }
}
