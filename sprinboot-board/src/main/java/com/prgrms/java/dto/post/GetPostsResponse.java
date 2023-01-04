package com.prgrms.java.dto.post;

import java.util.List;

public record GetPostsResponse(List<GetPostDetailsResponse> postDetails) {
}
