package com.programmers.jpaboard.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.jpaboard.board.controller.dto.BoardCreationDto;
import com.programmers.jpaboard.board.controller.dto.BoardUpdateDto;
import com.programmers.jpaboard.board.domian.Board;
import com.programmers.jpaboard.board.service.BoardService;
import com.programmers.jpaboard.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    BoardService boardService;

    private Board board;

    @Test
    @DisplayName("게시글 저장 요청을 한다")
    @Transactional
    public void saveCallTest() throws Exception {
        BoardCreationDto boardCreationDto = new BoardCreationDto();
        boardCreationDto.setTitle("title");
        boardCreationDto.setContent("content");

        mockMvc.perform(post("/boards")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardCreationDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("board-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("content")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("생성된 게시글"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("생성된 title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("생성된 content"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )));
    }

    @Test
    @DisplayName("게시글 수정 요청을 한다")
    @Transactional
    public void updateCallTest() throws Exception {
        BoardUpdateDto boardUpdateDto = new BoardUpdateDto();
        boardUpdateDto.setTitle("updatedTitle");
        boardUpdateDto.setContent("updatedContent");

        saveMember();

        mockMvc.perform(post("/boards/" + board.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(boardUpdateDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("board-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("updatedTitle"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("updatedContent")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("수정된 게시글"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("수정된 title"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("수정된 content"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )));
    }

    private void saveMember() {
        board = Board.builder()
                .title("title")
                .content("content")
                .build();

        Member member = Member.builder()
                .name("name")
                .age(10)
                .hobbies(List.of("Table Tennis"))
                .build();

        boardService.saveBoard(board, member);
    }

    @Test
    @DisplayName("모든 게시글을 요청한다")
    public void lookupAllCallTest() throws Exception {
        mockMvc.perform(get("/boards"))
                .andDo(print())
                .andDo(document("board-lookupAll",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data[]").type(JsonFieldType.ARRAY).description("게시글 리스트"),
                                fieldWithPath("data[].title").type(JsonFieldType.STRING).description("게시글 제목"),
                                fieldWithPath("data[].content").type(JsonFieldType.STRING).description("게시글 내용"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )));
    }

    @Test
    @DisplayName("한 개의 게시글을 요청한다")
    @Transactional
    public void lookUpOneCallTest() throws Exception {
        saveMember();

        mockMvc.perform(get("/boards/" + board.getId()))
                .andDo(print())
                .andDo(document("board-lookup",
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("응답 시간")
                        )));
    }
}