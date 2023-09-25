package org.prgrms.myboard.dto;

public record PostUpdateRequestDto(
    String title,
    String content
) {
}
