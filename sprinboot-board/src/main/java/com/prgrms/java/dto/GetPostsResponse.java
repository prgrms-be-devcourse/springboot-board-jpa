package com.prgrms.java.dto;

import java.util.List;
import java.util.Objects;

public class GetPostsResponse {

    private final List<GetPostDetailsResponse> postDetails;

    public GetPostsResponse(List<GetPostDetailsResponse> postDetails) {
        this.postDetails = postDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetPostsResponse that = (GetPostsResponse) o;
        return Objects.equals(postDetails, that.postDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postDetails);
    }
}
