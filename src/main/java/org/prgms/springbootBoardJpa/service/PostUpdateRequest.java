package org.prgms.springbootBoardJpa.service;

public record PostUpdateRequest(
    String title,
    String content
) {
}
