package com.prgrms.board.post.controller.dto;

import java.util.List;

public record PostDetailedResponses(List<PostDetailedResponse> list) {
    public PostDetailedResponses(List<PostDetailedResponse> list) {
        this.list = List.copyOf(list);
    }
}
