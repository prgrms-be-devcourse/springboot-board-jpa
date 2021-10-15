package org.prgms.board.user.dto;

import lombok.Getter;
import javax.validation.constraints.NotNull;

@Getter
public class UserIdRequest {
    @NotNull
    private Long userId;

    public UserIdRequest() {
    }

    public UserIdRequest(Long userId) {
        this.userId = userId;
    }
}
