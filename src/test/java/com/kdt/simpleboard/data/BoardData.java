package com.kdt.simpleboard.data;

import com.kdt.simpleboard.board.domain.Board;
import com.kdt.simpleboard.board.dto.BoardRequest;
import com.kdt.simpleboard.board.dto.BoardResponse;
import com.kdt.simpleboard.user.UserData;

import java.util.List;

public class BoardData {

    public static BoardRequest.CreateBoardRequest createBoardRequest(){
        return new BoardRequest.CreateBoardRequest(1L, "titleA", "contentA");
    }
    public static BoardRequest.CreateBoardRequest createBoardRequest(Long userId){
        return new BoardRequest.CreateBoardRequest(userId, "titleA", "contentA");
    }
    public static BoardRequest.ModifyBoard modifyBoardRequest(){
        return new BoardRequest.ModifyBoard("titleAChanged", "contentAChanged");
    }
    public static BoardResponse.CreateBoardRes createBoardResponse(){
        return new BoardResponse.CreateBoardRes(1L);
    }
    public static BoardResponse.FindBoardRes findBoardResponse(){
        return new BoardResponse.FindBoardRes(1L, "titleA", "contentA");
    }

    public static Board board(){
        return Board.builder()
                .title("titleA")
                .content("contentA")
                .user(UserData.user())
                .build();
    }

    public static Board board(String title, String content){
        return Board.builder()
                .title(title)
                .content(content)
                .user(UserData.user())
                .build();
    }

    public static List<Board> getBoards(){
        Board board1 = board();
        Board board2 = board("titleB", "contentB");

        return List.of(board1, board2);
    }
}
