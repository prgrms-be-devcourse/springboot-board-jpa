package com.prgrms.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class CursorResult {
    private List<PostResponseDto> postDtoList;
    private Boolean hasNext;

    public CursorResult(List<PostResponseDto> postDtoList, Boolean hasNext) {
        this.postDtoList = postDtoList;
        this.hasNext = hasNext;
    }
}
