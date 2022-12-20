package com.prgrms.jpa.controller.dto.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
@Builder
public class FindAllPostResponse {

    private final List<GetByIdPostResponse> posts;
}
