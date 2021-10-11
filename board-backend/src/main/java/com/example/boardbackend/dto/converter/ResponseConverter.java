package com.example.boardbackend.dto.converter;

import com.example.boardbackend.dto.response.UserIdResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseConverter {

    // value -> ResponseDto
    public UserIdResponse convertToUserId(Long id){
        return UserIdResponse.builder().id(id).build();
    }

}
