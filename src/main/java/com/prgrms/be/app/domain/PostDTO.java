package com.prgrms.be.app.domain;

public class PostDTO {
    public record CreateRequest(String title, String content, Long userId) {}

    public record UpdateRequest(String title, String content, Long userId) {}
}
