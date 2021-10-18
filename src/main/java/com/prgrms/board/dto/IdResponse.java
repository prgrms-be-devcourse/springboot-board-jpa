package com.prgrms.board.dto;

import com.prgrms.board.domain.User;
import com.prgrms.board.dto.user.UserFindRequest;
import lombok.Getter;

@Getter
public class IdResponse {

    private Long id;

    private IdResponse(Long id){
        this.id = id;
    }

    public static IdResponse from(Long id){
        return new IdResponse(id);
    }
}
