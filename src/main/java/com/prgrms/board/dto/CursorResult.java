package com.prgrms.board.dto;

import com.prgrms.board.dto.response.PostResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CursorResult {
    private final List<PostResponseDto> postDtoList;
    private final Boolean hasNext;

    public CursorResult(List<PostResponseDto> postDtoList, Boolean hasNext) {
        this.postDtoList = postDtoList;
        this.hasNext = hasNext;
    }
}
