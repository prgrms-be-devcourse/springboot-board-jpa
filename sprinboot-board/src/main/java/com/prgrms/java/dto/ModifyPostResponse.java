package com.prgrms.java.dto;

public class ModifyPostResponse {

    private final long modifiedId;

    public ModifyPostResponse(long createdId) {
        this.modifiedId = createdId;
    }

    public long getModifiedId() {
        return modifiedId;
    }
}
