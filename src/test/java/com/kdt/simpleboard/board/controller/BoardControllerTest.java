package com.kdt.simpleboard.board.controller;

import com.kdt.simpleboard.BaseIntegrationTest;
import com.kdt.simpleboard.board.domain.Board;
import com.kdt.simpleboard.board.repository.BoardRepository;
import com.kdt.simpleboard.data.BoardData;
import com.kdt.simpleboard.user.UserData;
import com.kdt.simpleboard.user.domain.User;
import com.kdt.simpleboard.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static com.kdt.simpleboard.board.dto.BoardRequest.*;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BoardControllerTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    @DisplayName("게시물 생성 api 호출에 성공한다")
    void createBoard() throws Exception {
        User savedUser = userRepository.save(UserData.user());
        CreateBoardRequest createBoardRequest = BoardData.createBoardRequest(savedUser.getId());
        mvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(asJsonString(createBoardRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.createdId").isNotEmpty());
    }

    @Test
    @DisplayName("게시물 업데이트 api 호출에 성공한다")
    void updateBoard() throws Exception {
        Board savedBoard = boardRepository.save(BoardData.board());
        userRepository.save(UserData.user());

        ModifyBoardRequest modifyBoardRequest = BoardData.modifyBoardRequest();
        mvc.perform(post("/posts/{id}", savedBoard.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(asJsonString(modifyBoardRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(modifyBoardRequest.title()))
                .andExpect(jsonPath("$.content").value(modifyBoardRequest.content()))
        ;
    }

    @Test
    @DisplayName("게시물 단건 조회 api 호출에 성공한다")
    void findBoardById() throws Exception{
        Board board = BoardData.board();
        boardRepository.save(board);
        mvc.perform(get("/posts/{id}", board.getId())
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(board.getTitle()))
                .andExpect(jsonPath("$.content").value(board.getContent()))
        ;
    }

    @Test
    @DisplayName("전체 게시물을 조회할 수 있다.")
    void findAll() throws Exception{
        User user = UserData.user();
        userRepository.save(user);

        List<Board> boards = BoardData.getBoards();
        ReflectionTestUtils.setField(boards.get(0), "user", user);
        ReflectionTestUtils.setField(boards.get(1), "user", user);
        boardRepository.saveAll(boards);


        mvc.perform(get("/posts")
                        .param("page","0")
                        .param("size", "10")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems", is(boards.size())))
                .andExpect(jsonPath("$.items[0].title",is(boards.get(0).getTitle()) ))
        ;
    }
}
