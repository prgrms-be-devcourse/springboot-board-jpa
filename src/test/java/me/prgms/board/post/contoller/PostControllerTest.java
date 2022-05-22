package me.prgms.board.post.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.prgms.board.domain.User;
import me.prgms.board.domain.post.Post;
import me.prgms.board.post.dto.CreatePostDto;
import me.prgms.board.post.dto.UpdatePostDto;
import me.prgms.board.post.repository.PostRepository;
import me.prgms.board.post.service.PostService;
import me.prgms.board.user.dto.UserDto;
import me.prgms.board.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setup() {
        user = new User("test name", 20, "test hobby");
        userRepository.save(user);
        userDto = new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장")
    void createTest() throws Exception {
        CreatePostDto postDto = new CreatePostDto("test-title", "test-content", userDto);

        mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-save",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용"),
                                fieldWithPath("userDto").type(JsonFieldType.OBJECT).description("userDto"),
                                fieldWithPath("userDto.id").type(JsonFieldType.NUMBER).description("userDto.id"),
                                fieldWithPath("userDto.name").type(JsonFieldType.STRING).description("userDto.name"),
                                fieldWithPath("userDto.age").type(JsonFieldType.NUMBER).description("userDto.age"),
                                fieldWithPath("userDto.hobby").type(JsonFieldType.STRING).description("userDto.hobby")
                        )));
    }

    @Test
    @DisplayName("게시글 저장 실패 - 제목이 없는 경우")
    void createTestFailByNoTitle() throws Exception {
        CreatePostDto postDto = new CreatePostDto("", "test-content", userDto);

        mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("제목을 비울 수 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 저장 실패 - 내용이 없는 경우")
    void createTestFailByNoContent() throws Exception {
        CreatePostDto postDto = new CreatePostDto("test-title", "", userDto);

        mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("내용을 비울 수 없습니다."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 저장 실패 - 유저가 없는 경우")
    void createTestFailByNoUser() throws Exception {
        CreatePostDto postDto = new CreatePostDto("test-title", "test-content", new UserDto(-20L, "test", 20, "test"));

        mockMvc.perform(post("/posts")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Id로 조회되는 유저가 없음"))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 수정")
    void updateTest() throws Exception {
        Long postId = postService.create(new CreatePostDto("test-title", "test-content", userDto));
        UpdatePostDto postDto = new UpdatePostDto("update-title", "update-content");

        mockMvc.perform(put("/posts/{postId}", postId)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("post-update",
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("내용")
                        )));

        Optional<Post> post = postRepository.findById(postId);
        assertAll(
                () -> assertThat(post.get().getTitle()).isEqualTo(postDto.getTitle()),
                () -> assertThat(post.get().getContent()).isEqualTo(postDto.getContent())
        );
    }

    @Test
    @DisplayName("게시글 수정 실패 - 게시글이 없는 경우")
    void updateTestFailByNoPost() throws Exception {
        Long postId = postService.create(new CreatePostDto("test-title", "test-content", userDto));
        UpdatePostDto postDto = new UpdatePostDto("update-title", "update-content");

        postId = -1L;

        mockMvc.perform(put("/posts/{postId}", postId)
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Id로 조회되는 게시글이 없음"))
                .andDo(print());
    }

    @Test
    @DisplayName("Id로 게시글 조회")
    void getPostByIdTest() throws Exception {
        Long postId = postService.create(new CreatePostDto("test-title", "test-content", userDto));

        mockMvc.perform(get("/posts/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("post-get"));
    }

    @Test
    @DisplayName("Id로 게시글 조회 실패 - 잘못된 ID 입력")
    void getPostByIdTestFail() throws Exception {
        Long postId = postService.create(new CreatePostDto("test-title", "test-content", userDto));

        postId = -1L;

        mockMvc.perform(get("/posts/{postId}", postId))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Id로 조회되는 게시글이 없음"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이징 파라미터가 없는 전체 조회")
    void getNoPagePostsTest() throws Exception {
        postService.create(new CreatePostDto("test-title", "test-content", userDto));
        postService.create(new CreatePostDto("test-title2", "test-content2", userDto));
        postService.create(new CreatePostDto("test-title3", "test-content3", userDto));

        mockMvc.perform(get("/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andDo(print());

    }

    @Test
    @DisplayName("페이징 파라미터를 이용한 조회")
    void getPagePostsTest() throws Exception {
        postService.create(new CreatePostDto("test-title", "test-content", userDto));
        postService.create(new CreatePostDto("test-title2", "test-content2", userDto));
        postService.create(new CreatePostDto("test-title3", "test-content3", userDto));

        mockMvc.perform(get("/posts")
                        .param("page", String.valueOf(0))
                        .param("size", String.valueOf(2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andDo(document("post-get-paging"));
    }

}