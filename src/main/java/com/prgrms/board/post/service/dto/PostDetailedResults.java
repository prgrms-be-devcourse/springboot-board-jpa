package com.prgrms.board.post.service.dto;

import java.util.List;

public record PostDetailedResults(List<PostDetailedResult> list) {
    public PostDetailedResults(List<PostDetailedResult> list) {
        this.list = List.copyOf(list);
    }
}
