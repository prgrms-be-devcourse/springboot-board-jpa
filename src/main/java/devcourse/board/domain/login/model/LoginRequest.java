package devcourse.board.domain.login.model;

import javax.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
