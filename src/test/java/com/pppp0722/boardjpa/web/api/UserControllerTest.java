package com.pppp0722.boardjpa.web.api;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pppp0722.boardjpa.service.user.UserService;
import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void 사용자를_저장하면_객체를_반환한다() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequestDto)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("user-create",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                )
            ));
    }

    @Test
    public void 사용자를_조회하면_객체를_반환한다() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        mockMvc.perform(get("/users/{id}", userResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("user-getById",
                responseFields(
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                )
            ));
    }

    @Test
    public void 사용자를_전체_조회하면_객체_리스트를_반환한다() throws Exception {
        UserRequestDto userRequestDto1 = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserRequestDto userRequestDto2 = UserRequestDto.builder()
            .name("Hwanil Kim")
            .age(72)
            .hobby("Study")
            .build();

        userService.save(userRequestDto1);
        userService.save(userRequestDto2);

        mockMvc.perform(get("/users")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "id,DESC")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("user-getAll",
                responseFields(
                    fieldWithPath("content[]").type(JsonFieldType.ARRAY)
                        .description("content[]"),
                    fieldWithPath("content[].createdAt").type(JsonFieldType.STRING)
                        .description("content[].createdAt"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER)
                        .description("content[].id"),
                    fieldWithPath("content[].name").type(JsonFieldType.STRING)
                        .description("content[].name"),
                    fieldWithPath("content[].age").type(JsonFieldType.NUMBER)
                        .description("content[].age"),
                    fieldWithPath("content[].hobby").type(JsonFieldType.STRING)
                        .description("content[].hobby"),
                    fieldWithPath("pageable").type(JsonFieldType.OBJECT)
                        .description("pageable"),
                    fieldWithPath("pageable.sort").type(JsonFieldType.OBJECT)
                        .description("pageable.sort"),
                    fieldWithPath("pageable.sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("pageable.sort.empty"),
                    fieldWithPath("pageable.sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("pageable.sort.unsorted"),
                    fieldWithPath("pageable.sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("pageable.sort.sorted"),
                    fieldWithPath("pageable.offset").type(JsonFieldType.NUMBER)
                        .description("pageable.offset"),
                    fieldWithPath("pageable.pageNumber").type(JsonFieldType.NUMBER)
                        .description("pageable.pageNumber"),
                    fieldWithPath("pageable.pageSize").type(JsonFieldType.NUMBER)
                        .description("pageable.pageSize"),
                    fieldWithPath("pageable.paged").type(JsonFieldType.BOOLEAN)
                        .description("pageable.paged"),
                    fieldWithPath("pageable.unpaged").type(JsonFieldType.BOOLEAN)
                        .description("pageable.unpaged"),
                    fieldWithPath("last").type(JsonFieldType.BOOLEAN).description("last"),
                    fieldWithPath("totalPages").type(JsonFieldType.NUMBER)
                        .description("totalPages"),
                    fieldWithPath("totalElements").type(JsonFieldType.NUMBER)
                        .description("totalElements"),
                    fieldWithPath("size").type(JsonFieldType.NUMBER).description("size"),
                    fieldWithPath("number").type(JsonFieldType.NUMBER)
                        .description("number"),
                    fieldWithPath("sort").type(JsonFieldType.OBJECT).description("sort"),
                    fieldWithPath("sort.empty").type(JsonFieldType.BOOLEAN)
                        .description("sort.empty"),
                    fieldWithPath("sort.unsorted").type(JsonFieldType.BOOLEAN)
                        .description("sort.unsorted"),
                    fieldWithPath("sort.sorted").type(JsonFieldType.BOOLEAN)
                        .description("sort.sorted"),
                    fieldWithPath("first").type(JsonFieldType.BOOLEAN)
                        .description("first"),
                    fieldWithPath("numberOfElements").type(JsonFieldType.NUMBER)
                        .description("numberOfElements"),
                    fieldWithPath("empty").type(JsonFieldType.BOOLEAN)
                        .description("empty")
                )
            ));
    }

    @Test
    public void 사용자를_수정하면_객체를_반환한다() throws Exception {
        UserRequestDto userCreateRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userCreateRequestDto);

        UserRequestDto userUpdateRequestDto = UserRequestDto.builder()
            .name("Hwanil Kim")
            .age(72)
            .hobby("Study")
            .build();

        mockMvc.perform(put("/users/{id}", userResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userUpdateRequestDto)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("user-update",
                requestFields(
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                ),
                responseFields(
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                    fieldWithPath("age").type(JsonFieldType.NUMBER).description("age"),
                    fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                )
            ));
    }

    @Test
    public void 사용자를_삭제() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        mockMvc.perform(delete("/users/{id}", userResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("user-delete"));
    }
}
