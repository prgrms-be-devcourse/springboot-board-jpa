package com.kdt.simpleboard.board.dto;

import com.kdt.simpleboard.board.domain.Board;
import com.kdt.simpleboard.user.domain.User;

import static com.kdt.simpleboard.board.dto.BoardRequest.*;
import static com.kdt.simpleboard.board.dto.BoardResponse.*;

public class BoardMapper {
    public static CreateBoardRes toCreateBoardRes(Board board){
        return new CreateBoardRes(board.getId());
    }
    public static Board toBoardEntity(CreateBoardRequest request, User user){
        return Board.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .build();
    }
    public static BoardResponse.FindBoardRes toFindBoardRes(Board board){
        return FindBoardRes.builder()
                .userId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .build();
    }
}
