package org.programmers.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = {BoardApiController.class})
class BoardApiControllerTest {

    @MockBean
    private BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 생성")
    void testCreateBoard() {

    }

    @Test
    @DisplayName("게시글 전체 조회")
    void testGetAllBoard() {

    }

    @Test
    @DisplayName("게시글 단건 조회")
    void testGetOneBoard() {

    }

    @Test
    @DisplayName("게시글 단건 조회 - 실패")
    void testGetOneBoardFail() {

    }

    @Test
    @DisplayName("게시글 업데이트 - 성공")
    void testUpdateBoard() {

    }

    @Test
    @DisplayName("게시글 업데이트 - 실패")
    void testUpdatedBoardFail() {

    }
}