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
import com.pppp0722.boardjpa.domain.post.PostRepository;
import com.pppp0722.boardjpa.domain.user.UserRepository;
import com.pppp0722.boardjpa.service.post.PostService;
import com.pppp0722.boardjpa.service.user.UserService;
import com.pppp0722.boardjpa.web.dto.PostRequestDto;
import com.pppp0722.boardjpa.web.dto.PostResponseDto;
import com.pppp0722.boardjpa.web.dto.UserRequestDto;
import com.pppp0722.boardjpa.web.dto.UserResponseDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 게시글을_저장하면_객체를_반환한다() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postRequestDto)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("post-create",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("userResponseDto"),
                    fieldWithPath("userResponseDto.createdAt").type(JsonFieldType.STRING)
                        .description("userResponseDto.createdAt"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.id"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("userResponseDto.name"),
                    fieldWithPath("userResponseDto.age").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.age"),
                    fieldWithPath("userResponseDto.hobby").type(JsonFieldType.STRING)
                        .description("userResponseDto.hobby")
                ),
                responseFields(
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("userResponseDto"),
                    fieldWithPath("userResponseDto.createdAt").type(JsonFieldType.STRING)
                        .description("userResponseDto.createdAt"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.id"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("userResponseDto.name"),
                    fieldWithPath("userResponseDto.age").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.age"),
                    fieldWithPath("userResponseDto.hobby").type(JsonFieldType.STRING)
                        .description("userResponseDto.hobby")
                )
            ));
    }

    @Test
    public void 게시글을_조회하면_객체를_반환한다() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto postResponseDto = postService.save(postRequestDto);

        System.out.println(postResponseDto.getId());

        mockMvc.perform(get("/posts/{id}", postResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-getById",
                responseFields(
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("userResponseDto"),
                    fieldWithPath("userResponseDto.createdAt").type(JsonFieldType.STRING)
                        .description("userResponseDto.createdAt"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.id"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("userResponseDto.name"),
                    fieldWithPath("userResponseDto.age").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.age"),
                    fieldWithPath("userResponseDto.hobby").type(JsonFieldType.STRING)
                        .description("userResponseDto.hobby")
                )
            ));
    }

    @Test
    public void 게시글을_전체_조회하면_객체_리스트를_반환한다() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto1 = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostRequestDto postRequestDto2 = PostRequestDto.builder()
            .title("Bye")
            .content("See you again.")
            .userResponseDto(userResponseDto)
            .build();

        postService.save(postRequestDto1);
        postService.save(postRequestDto2);

        mockMvc.perform(get("/posts")
                .param("page", "0")
                .param("size", "5")
                .param("sort", "id,DESC")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-getAll",
                responseFields(
                    fieldWithPath("content[]").type(JsonFieldType.ARRAY)
                        .description("content[]"),
                    fieldWithPath("content[].createdAt").type(JsonFieldType.STRING)
                        .description("content[].createdAt"),
                    fieldWithPath("content[].id").type(JsonFieldType.NUMBER)
                        .description("content[].id"),
                    fieldWithPath("content[].title").type(JsonFieldType.STRING)
                        .description("content[].title"),
                    fieldWithPath("content[].content").type(JsonFieldType.STRING)
                        .description("content[].content"),
                    fieldWithPath("content[].userResponseDto").type(JsonFieldType.OBJECT)
                        .description("content[].userResponseDto"),
                    fieldWithPath("content[].userResponseDto.createdAt").type(JsonFieldType.STRING)
                        .description("content[].userResponseDto.createdAt"),
                    fieldWithPath("content[].userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("content[].userResponseDto.id"),
                    fieldWithPath("content[].userResponseDto.name").type(JsonFieldType.STRING)
                        .description("content[].userResponseDto.name"),
                    fieldWithPath("content[].userResponseDto.age").type(JsonFieldType.NUMBER)
                        .description("content[].userResponseDto.age"),
                    fieldWithPath("content[].userResponseDto.hobby").type(JsonFieldType.STRING)
                        .description("content[].userResponseDto.hobby"),
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
    public void 게시글을_수정하면_객체를_반환한다() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto postResponseDto = postService.save(postRequestDto);

        PostRequestDto postUpdateRequestDto = PostRequestDto.builder()
            .title("Bye")
            .content("See you again.")
            .userResponseDto(userResponseDto)
            .build();

        mockMvc.perform(put("/posts/{id}", postResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postUpdateRequestDto)))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("post-update",
                requestFields(
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("userResponseDto"),
                    fieldWithPath("userResponseDto.createdAt").type(JsonFieldType.STRING)
                        .description("userResponseDto.createdAt"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.id"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("userResponseDto.name"),
                    fieldWithPath("userResponseDto.age").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.age"),
                    fieldWithPath("userResponseDto.hobby").type(JsonFieldType.STRING)
                        .description("userResponseDto.hobby")
                ),
                responseFields(
                    fieldWithPath("createdAt").type(JsonFieldType.STRING).description("createdAt"),
                    fieldWithPath("id").type(JsonFieldType.NUMBER).description("id"),
                    fieldWithPath("title").type(JsonFieldType.STRING).description("title"),
                    fieldWithPath("content").type(JsonFieldType.STRING).description("content"),
                    fieldWithPath("userResponseDto").type(JsonFieldType.OBJECT)
                        .description("userResponseDto"),
                    fieldWithPath("userResponseDto.createdAt").type(JsonFieldType.STRING)
                        .description("userResponseDto.createdAt"),
                    fieldWithPath("userResponseDto.id").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.id"),
                    fieldWithPath("userResponseDto.name").type(JsonFieldType.STRING)
                        .description("userResponseDto.name"),
                    fieldWithPath("userResponseDto.age").type(JsonFieldType.NUMBER)
                        .description("userResponseDto.age"),
                    fieldWithPath("userResponseDto.hobby").type(JsonFieldType.STRING)
                        .description("userResponseDto.hobby")
                )
            ));
    }

    @Test
    public void 게시글을_삭제() throws Exception {
        UserRequestDto userRequestDto = UserRequestDto.builder()
            .name("Ilhwan Lee")
            .age(27)
            .hobby("Game")
            .build();

        UserResponseDto userResponseDto = userService.save(userRequestDto);

        PostRequestDto postRequestDto = PostRequestDto.builder()
            .title("Hi")
            .content("Nice to meet you.")
            .userResponseDto(userResponseDto)
            .build();

        PostResponseDto postResponseDto = postService.save(postRequestDto);

        mockMvc.perform(delete("/posts/{id}", postResponseDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent())
            .andDo(print())
            .andDo(document("post-delete"));
    }
}