package org.programmers.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.dto.BoardCreateRequest;
import org.programmers.board.dto.BoardUpdateRequest;
import org.programmers.board.dto.UserDTO;
import org.programmers.board.entity.Board;
import org.programmers.board.entity.User;
import org.programmers.board.entity.vo.Content;
import org.programmers.board.entity.vo.Name;
import org.programmers.board.entity.vo.Title;
import org.programmers.board.repository.BoardRepository;
import org.programmers.board.repository.UserRepository;
import org.programmers.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BoardApiControllerTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void cleanall() {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 생성")
    void testCreateBoard() throws Exception {
        //given
        BoardCreateRequest boardCreateRequest = new BoardCreateRequest(
                "this is title",
                "this is content",
                new UserDTO("김지웅",
                        27,
                        "독서"));

        ResultActions resultActions = this.mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(boardCreateRequest)));

        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value("this is title"))
                .andExpect(jsonPath("content").value("this is content"));
    }

    @Test
    @DisplayName("게시글 전체 조회")
    void testGetAllBoard() throws Exception {
        mockMvc.perform(get("/posts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 단건 조회")
    void testGetOneBoard() throws Exception {
        Long boardId = boardService.createBoard(
                new Board(new Title("this is title"),
                        new Content("this is Content"),
                        new User(new Name("kim"),
                                27,
                                "soccer")));

        mockMvc.perform(get("/posts/{id}", boardId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 업데이트")
    @Transactional
    void testUpdateBoard() throws Exception {
        Long boardId = boardService.createBoard(new Board(new Title("this is title"), new Content("this is Content"), new User(new Name("kim"),
                27,
                "soccer")));
        BoardUpdateRequest boardUpdateRequest = new BoardUpdateRequest("update-title", "update-content");

        mockMvc.perform(patch("/posts/{id}", boardId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boardUpdateRequest)))
                .andExpect(jsonPath("title").value("update-title"))
                .andExpect(jsonPath("content").value("update-content"))
                .andDo(print());
    }
}