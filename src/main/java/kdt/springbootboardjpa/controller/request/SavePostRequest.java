package kdt.springbootboardjpa.controller.request;

import lombok.Builder;

public record SavePostRequest(String title, String content, Long createdBy) {
    @Builder
    public SavePostRequest {
    }
}
