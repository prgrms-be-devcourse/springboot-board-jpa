package com.prgrms.java;

public class ModifyPostResponse {

    private final long modifiedId;

    public ModifyPostResponse(long createdId) {
        this.modifiedId = createdId;
    }

    public long getModifiedId() {
        return modifiedId;
    }
}
