package com.kdt.simpleboard.data;

import com.kdt.simpleboard.board.domain.Board;
import com.kdt.simpleboard.board.dto.BoardRequest;
import com.kdt.simpleboard.board.dto.BoardResponse;

public class BoardData {
    public static BoardRequest.CreateBoardRequest getCreateBoardRequest(){
        return new BoardRequest.CreateBoardRequest(1L, "titleA", "contentA");
    }
    public static BoardRequest.ModifyBoard getModifyBoardRequest(){
        return new BoardRequest.ModifyBoard("titleAChanged", "contentAChanged");
    }
    public static BoardResponse.CreateBoardRes getCreateBoardResponse(){
        return new BoardResponse.CreateBoardRes(1L);
    }
    public static BoardResponse.FindBoardRes getFindBoardResponse(){
        return new BoardResponse.FindBoardRes(1L, "titleA", "contentA");
    }
}
