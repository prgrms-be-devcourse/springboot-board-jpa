package com.example.boardbackend.dto.request;

import lombok.*;

@Builder
@Getter
public class UpdateViewRequest {
    private Long newView;
}
