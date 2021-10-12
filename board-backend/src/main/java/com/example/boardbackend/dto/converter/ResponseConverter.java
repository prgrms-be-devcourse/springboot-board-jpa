package com.example.boardbackend.dto.converter;

import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.response.BoardResponse;
import com.example.boardbackend.dto.response.UserIdResponse;
import org.springframework.stereotype.Component;

@Component
public class ResponseConverter {

    // value -> ResponseDto

    public UserIdResponse convertToUserId(Long id){
        return UserIdResponse.builder().id(id).build();
    }

    public BoardResponse convertToBoard(PostDto postDto){
        return BoardResponse.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .view(postDto.getView())
                .createdBy(postDto.getUserDto().getName())
                .build();
    }
}
