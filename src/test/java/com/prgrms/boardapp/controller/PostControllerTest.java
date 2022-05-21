package com.prgrms.boardapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.prgrms.boardapp.common.PostCreateUtil.createPostDto;
import static com.prgrms.boardapp.controller.DocumentInfo.*;
import static com.prgrms.boardapp.controller.PageableInfo.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    PostDto postDto = createPostDto();

    @Test
    @DisplayName("새로운 Post를 생성하는 API 호출")
    void testSave() throws Exception {
        given(postService.save(postDto)).willReturn(postDto.getId());

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postDto.getId()))
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath(ID.getField()).type(JsonFieldType.NUMBER).description(ID.getDescription()),
                                fieldWithPath(TITLE.getField()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                                fieldWithPath(CONTENT.getField()).type(JsonFieldType.STRING).description(CONTENT.getDescription()),
                                fieldWithPath(CREATED_AT.getField()).type(JsonFieldType.STRING).description(CREATED_AT.getDescription()),
                                fieldWithPath(USER_DTO.getField()).type(JsonFieldType.OBJECT).description(USER_DTO.getDescription()),
                                fieldWithPath(USER_DTO_ID.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_ID.getDescription()),
                                fieldWithPath(USER_DTO_NAME.getField()).type(JsonFieldType.STRING).description(USER_DTO_NAME.getDescription()),
                                fieldWithPath(USER_DTO_AGE.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_AGE.getDescription()),
                                fieldWithPath(USER_DTO_HOBBY.getField()).type(JsonFieldType.STRING).description(USER_DTO_HOBBY.getDescription())
                        ),
                        responseFields(
                                fieldWithPath(POST_ID.getField()).type(JsonFieldType.NUMBER).description(POST_ID.getDescription())
                        )
                ));
    }

    @Test
    @DisplayName("postId를 pathVariable로 API 조회할 수 있다.")
    void testFindById() throws Exception {
        given(postService.findById(postDto.getId())).willReturn(postDto);

        mockMvc.perform(get("/posts/{postId}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findById",
                        responseFields(
                                fieldWithPath(ID.getField()).type(JsonFieldType.NUMBER).description(ID.getDescription()),
                                fieldWithPath(TITLE.getField()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                                fieldWithPath(CONTENT.getField()).type(JsonFieldType.STRING).description(CONTENT.getDescription()),
                                fieldWithPath(CREATED_AT.getField()).type(JsonFieldType.STRING).description(CREATED_AT.getDescription()),
                                fieldWithPath(USER_DTO.getField()).type(JsonFieldType.OBJECT).description(USER_DTO.getDescription()),
                                fieldWithPath(USER_DTO_ID.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_ID.getDescription()),
                                fieldWithPath(USER_DTO_NAME.getField()).type(JsonFieldType.STRING).description(USER_DTO_NAME.getDescription()),
                                fieldWithPath(USER_DTO_AGE.getField()).type(JsonFieldType.NUMBER).description(USER_DTO_AGE.getDescription()),
                                fieldWithPath(USER_DTO_HOBBY.getField()).type(JsonFieldType.STRING).description(USER_DTO_HOBBY.getDescription())
                        )
                ));

    }

    @Test
    @DisplayName("페이징 처리하여 모든 데이터를 조회할 수 있다.")
    void testFindAllWithPaging() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<PostDto> posts = List.of(
                createPostDto(),
                createPostDto()
        );

        Page<PostDto> pageResponse = new PageImpl<>(posts);
        given(postService.findAll(pageRequest)).willReturn(pageResponse);
        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(pageRequest.getPageNumber()))
                .param("size", String.valueOf(pageRequest.getPageSize()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-findAll",
                        responseFields(
                                fieldWithPath(CONTENT_ARRAY.getField()).type(JsonFieldType.ARRAY).description(CONTENT_ARRAY.getDescription()),
                                fieldWithPath(ID.getFieldPrefixContentArr()).type(JsonFieldType.NUMBER).description(ID.getDescription()),
                                fieldWithPath(USER_DTO.getFieldPrefixContentArr()).type(JsonFieldType.OBJECT).description(USER_DTO.getDescription()),
                                fieldWithPath(USER_DTO_ID.getFieldPrefixContentArr()).type(JsonFieldType.NUMBER).description(USER_DTO_ID.getDescription()),
                                fieldWithPath(USER_DTO_NAME.getFieldPrefixContentArr()).type(JsonFieldType.STRING).description(USER_DTO_NAME.getDescription()),
                                fieldWithPath(USER_DTO_AGE.getFieldPrefixContentArr()).type(JsonFieldType.NUMBER).description(USER_DTO_AGE.getDescription()),
                                fieldWithPath(USER_DTO_HOBBY.getFieldPrefixContentArr()).type(JsonFieldType.STRING).description(USER_DTO_HOBBY.getDescription()),
                                fieldWithPath(TITLE.getFieldPrefixContentArr()).type(JsonFieldType.STRING).description(TITLE.getDescription()),
                                fieldWithPath(CONTENT.getFieldPrefixContentArr()).type(JsonFieldType.STRING).description(CONTENT.getDescription()),
                                fieldWithPath(CREATED_AT.getFieldPrefixContentArr()).type(JsonFieldType.STRING).description(CREATED_AT.getDescription()),

                                fieldWithPath(PAGEABLE.getField()).type(JsonFieldType.STRING).description(PAGEABLE.getDescription()),
                                fieldWithPath(LAST.getField()).type(JsonFieldType.BOOLEAN).description(LAST.getDescription()),
                                fieldWithPath(TOTAL_PAGES.getField()).type(JsonFieldType.NUMBER).description(TOTAL_PAGES.getDescription()),
                                fieldWithPath(TOTAL_ELEMENTS.getField()).type(JsonFieldType.NUMBER).description(TOTAL_ELEMENTS.getDescription()),
                                fieldWithPath(SIZE.getField()).type(JsonFieldType.NUMBER).description(SIZE.getDescription()),
                                fieldWithPath(SORT.getField()).type(JsonFieldType.OBJECT).description(SORT.getDescription()),
                                fieldWithPath(SORT_EMPTY.getField()).type(JsonFieldType.BOOLEAN).description(SORT_EMPTY.getDescription()),
                                fieldWithPath(SORT_SORTED.getField()).type(JsonFieldType.BOOLEAN).description(SORT_SORTED.getDescription()),
                                fieldWithPath(SORT_UNSORTED.getField()).type(JsonFieldType.BOOLEAN).description(SORT_UNSORTED.getDescription()),
                                fieldWithPath(NUMBER.getField()).type(JsonFieldType.NUMBER).description(NUMBER.getDescription()),
                                fieldWithPath(FIRST.getField()).type(JsonFieldType.BOOLEAN).description(FIRST.getDescription()),
                                fieldWithPath(NUM_OF_ELEMENTS.getField()).type(JsonFieldType.NUMBER).description(NUM_OF_ELEMENTS.getDescription()),
                                fieldWithPath(EMPTY.getField()).type(JsonFieldType.BOOLEAN).description(EMPTY.getDescription())
                        )
                ));
    }

    @Test
    @DisplayName("존재하지 않는 아이디는 404를 반환")
    void testFindById404() throws Exception {
        given(postService.findById(postDto.getId())).willThrow(new EntityNotFoundException("Throws EntityNotFoundException Message"));

        mockMvc.perform(get("/posts/{postId}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("서버 장의 경우 500을 반환")
    void testFindById500() throws Exception {
        given(postService.findById(postDto.getId())).willThrow(new RuntimeException("Throws RuntimeException Message"));

        mockMvc.perform(get("/posts/{postId}", postDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isInternalServerError())
                .andDo(print());
    }

}