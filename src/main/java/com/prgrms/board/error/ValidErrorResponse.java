package com.prgrms.board.error;

import lombok.Builder;

public record ValidErrorResponse(String field, String message) {

    @Builder
    public ValidErrorResponse {
    }

}
