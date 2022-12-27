package com.prgrms.java.dto;

import java.util.List;

public record GetPostsResponse(List<GetPostDetailsResponse> postDetails) {
}
