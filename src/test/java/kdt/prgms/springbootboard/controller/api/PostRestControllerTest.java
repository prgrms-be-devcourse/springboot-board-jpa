package kdt.prgms.springbootboard.controller.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import java.util.stream.*;
import kdt.prgms.springbootboard.converter.UserConverter;
import kdt.prgms.springbootboard.domain.User;
import kdt.prgms.springbootboard.dto.PostDto;

import kdt.prgms.springbootboard.repository.UserRepository;
import kdt.prgms.springbootboard.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostService postService;

    @Autowired
    UserConverter userConverter;

    @Autowired
    ObjectMapper objectMapper;

    User testUser;


    @BeforeAll
    void setup() {
        testUser = userRepository.save(User.createUser("tester", 20));
    }

    @Test
    void 게시글_생성_요청_성공() throws Exception {
        // given
        var postDto = createPostDto(testUser, 1).get(0);

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    void 게시글이_제목이_유효하지_않는경우_게시글생성_요청_실패가_정상() throws Exception {
        // given
        var postDto = createPostDto(testUser, 1).get(0);
        postDto.setTitle("");

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isBadRequest())
            .andDo(print());

        postDto = createPostDto(testUser, 1).get(0);
        String title = IntStream.range(0, 10).mapToObj(i -> "Very Long Title.")
            .collect(Collectors.joining());
        postDto.setTitle(title);

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }

    @Test
    void 게시글의_작성자가_존재하지_않으면_생성_요청_실패가_정상() throws Exception {
        // given
        var postDto = createPostDto(
            User.createUser("non exist user", 20), 1
        ).get(0);

        // when, then
        mockMvc.perform(post("/api/posts/v1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isBadRequest())
            .andDo(print());
    }


    @Test
    void 게시글_페이지_요청_성공() throws Exception {
        // given
        createPostDto(testUser, 30).forEach(postDto -> postService.save(postDto));

        // when, then
        mockMvc.perform(get("/api/posts/v1")
            .param("page", "2")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void 게시글_단건_요청_성공() throws Exception {
        // given
        var postId = postService.save(createPostDto(testUser, 1).get(0));

        // when, then
        mockMvc.perform(get("/api/posts/v1/{id}", postId)
            .param("page", "2")
            .param("size", "10")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    void 게시글_수정_요청_성공() throws Exception {
        // given
        var postDto = createPostDto(testUser, 1).get(0);
        var postId = postService.save(postDto);
        System.out.println("save end");

        postDto.setTitle("updated " + postDto.getTitle());
        postDto.setContent("updated " + postDto.getContent());

        mockMvc.perform(post("/api/posts/v1/{id}", postId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(postDto)))
            .andExpect(status().isOk())
            .andDo(print());
    }


    private List<PostDto> createPostDto(User user, int count) {
        return IntStream.range(0, count).mapToObj(i -> new PostDto(
            "testTitle#" + i,
            "testContent#" + i,
            userConverter.convertUserDto(user)
        )).collect(Collectors.toCollection(ArrayList::new));
    }

}