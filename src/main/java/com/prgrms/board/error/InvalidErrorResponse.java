package com.prgrms.board.error;

import lombok.Builder;

public record InvalidErrorResponse(String field, String message) {

    @Builder
    public InvalidErrorResponse {
    }

}
