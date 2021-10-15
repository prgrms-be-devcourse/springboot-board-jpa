package com.prgrms.board.dto;

import lombok.Getter;

@Getter
public class IdResponse {

    private Long id;

    public IdResponse(Long id){
        this.id = id;
    }
}
