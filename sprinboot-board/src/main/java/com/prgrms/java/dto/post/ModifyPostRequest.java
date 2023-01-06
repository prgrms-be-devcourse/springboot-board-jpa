package com.prgrms.java.dto.post;

import jakarta.validation.constraints.NotBlank;

public record ModifyPostRequest(@NotBlank String title, @NotBlank String content) {

}
