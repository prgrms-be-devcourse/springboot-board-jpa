package com.prgrms.java.dto;

public class CreatePostResponse {

    private final long createdId;

    public CreatePostResponse(long createdId) {
        this.createdId = createdId;
    }

    public long getCreatedId() {
        return createdId;
    }
}
