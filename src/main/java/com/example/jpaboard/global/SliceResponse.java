package com.example.jpaboard.global;

import com.example.jpaboard.post.service.dto.PostResponse;
import org.springframework.data.domain.Slice;

public class SliceResponse<T> extends ApiResponse{

    private Slice<T> data;

    public SliceResponse(Slice<T> data, SuccessCode successCode) {
        super(data, successCode);
    }

}
