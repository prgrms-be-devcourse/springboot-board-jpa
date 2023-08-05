package com.example.jpaboard.global;

import org.springframework.data.domain.Slice;

public class SliceResponse<T> extends ApiResponse{

    private Slice<T> data;

    public SliceResponse(Slice<T> data, SuccessCode successCode) {
        super(data, successCode);
    }

}
