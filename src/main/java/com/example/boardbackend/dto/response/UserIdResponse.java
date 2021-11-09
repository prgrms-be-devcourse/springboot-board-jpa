package com.example.boardbackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class UserIdResponse {
    private Long id;

    static public UserIdResponse of(Long id){
        return UserIdResponse.builder()
                .id(id)
                .build();
    }
}
