package prgrms.project.post.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.domain.post.Post;
import prgrms.project.post.domain.user.Hobby;
import prgrms.project.post.domain.user.User;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.repository.UserRepository;
import prgrms.project.post.service.post.PostDto;
import prgrms.project.post.service.post.PostService;
import prgrms.project.post.util.mapper.UserMapper;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@SpringBootTest
class GlobalExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostService postService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserMapper userMapper;

    User savedUser;
    Long postId;

    @BeforeEach
    void setup() {
        User user = User.builder().name("name").age(10).hobbies(Set.of(new Hobby("swim"))).build();
        savedUser = userRepository.save(user);

        var post = Post.builder().title("title").content("content").user(savedUser).build();
        var savedPost = postRepository.save(post);
        postId = savedPost.getId();
    }

    @Test
    @Transactional
    @DisplayName("밸리데이션에 맞지 않는 입력값이 들어올 경우 예외를 처리한다.")
    void testHandleMethodArgumentNotValid() throws Exception {
        var postDto = PostDto.builder()
            .title("")
            .content("this post dont have title")
            .user(userMapper.toDto(savedUser))
            .build();

        var requestString = objectMapper.writeValueAsString(postDto);

        mockMvc.perform(post("/api/v1/posts").content(requestString).contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(document("post-invalid-input",
                responseFields(
                    fieldWithPath("error").type(STRING).description("에러"),
                    fieldWithPath("errorMessage").type(STRING).description("에러메시지")
                )
            )
        );
    }

    @Test
    @Transactional
    @DisplayName("지원하지 않는 미디어 타입을 요청하는 경우 예외를 처리한다.")
    void testHandleHttpMediaTypeNotSupported() throws Exception {
        var postDto = PostDto.builder()
            .title("title")
            .content("content")
            .user(userMapper.toDto(savedUser))
            .build();

        var requestString = objectMapper.writeValueAsString(postDto);

        mockMvc.perform(post("/api/v1/posts").content(requestString).contentType(APPLICATION_PDF))
            .andExpect(status().isUnsupportedMediaType())
            .andDo(document("post-unsupported-media-type",
                responseFields(
                    fieldWithPath("error").type(STRING).description("에러"),
                    fieldWithPath("errorMessage").type(STRING).description("에러메시지")
                )
            )
        );
    }

    @Test
    @Transactional
    @DisplayName("파라미터의 데이터 타입이 맞지않을 경우 예외를 처리한다.")
    void testHandleTypeMismatch() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", "mismatch").contentType(APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andDo(document("post-mismatch-type",
                responseFields(
                    fieldWithPath("error").type(STRING).description("에러"),
                    fieldWithPath("errorMessage").type(STRING).description("에러메시지")
                )
            )
        );
    }

    @Test
    @Transactional
    @DisplayName("엔티티를 찾지 못하는 경우 예외를 처리한다.")
    void testHandleNoSuchElementException() throws Exception {
        mockMvc.perform(get("/api/v1/posts/{postId}", 999999).contentType(APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andDo(document("post-not-found",
                responseFields(
                    fieldWithPath("error").type(STRING).description("에러"),
                    fieldWithPath("errorMessage").type(STRING).description("에러메시지")
                )
            )
        );
    }
}