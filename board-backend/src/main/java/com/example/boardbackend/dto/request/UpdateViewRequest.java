package com.example.boardbackend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@NoArgsConstructor  // patch 요청에서 기본생성자 없으면 json 직렬화 실패
//@AllArgsConstructor
public class UpdateViewRequest {
    private Long newView;
}
