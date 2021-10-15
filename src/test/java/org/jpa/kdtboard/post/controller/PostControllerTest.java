package org.jpa.kdtboard.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jpa.kdtboard.domain.board.*;
import org.jpa.kdtboard.post.converter.PostConverter;
import org.jpa.kdtboard.post.dto.PostDto;
import org.jpa.kdtboard.post.dto.PostType;
import org.jpa.kdtboard.post.service.PostService;
import org.jpa.kdtboard.user.converter.UserConverter;
import org.jpa.kdtboard.user.dto.UserDto;
import org.jpa.kdtboard.user.service.UserService;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by yunyun on 2021/10/14.
 */

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
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
    private PostConverter postConverter;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    final String defaultUserName = "홍길동";
    User userEntity;

    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        var userDto = UserDto.builder()
                .name(defaultUserName)
                .age(100)
                .createdBy("관리자")
                .hobby("코딩")
                .build();

        userEntity = userConverter.convertEntity(userDto);


        userService.save(userDto);

        postService.save(PostDto.builder()
                        .postType(PostType.HOMEWORK)
                        .content("정확히 이야기하면 Entity는 반드시 파라미터가 없는 생성자가 있어야 하고...")
                        .title("JPA 과제 제출합니다.")
                        .createdBy(defaultUserName)
                        .password("1234")
                        .homeworkStatus(HomeworkStatus.COMPLETED)
                        .build());

        postService.save(PostDto.builder()
                .postType(PostType.INTRODUCE)
                .content("사실 저는 개명하였습니다. 그래서 본명은...")
                .title("잘 부탁드립니다. 홍길동 입니다.")
                .createdBy(defaultUserName)
                .password("1234")
                .build());

        postService.save(PostDto.builder()
                .postType(PostType.QUESTION)
                .content("왜 Entity는 기본 생성자가 필요한지 궁금합니다. 그리고 더욱이..")
                .title("Entity 관련 질문이 있습니다.")
                .createdBy(defaultUserName)
                .password("1234")
                .postScope(PostScope.PUBLIC)
                .build());

        postService.save(PostDto.builder()
                .postType(PostType.NOTICE)
                .content("장소와 시간은 아래와 같습니다. 회비는...")
                .title("오늘 번개모임이 있습니다. 확인바랍니다.")
                .createdBy(defaultUserName)
                .password("1234")
                .expireDate(LocalDateTime.of(2021, 12,25, 12, 00, 00))
                .build());

    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("페이징 요청과 함께 전체 게시물의 정보를 요청하여, 정보를 전달받을 수 있다.")
    void getAllWithPagingTest() throws Exception {
        //When, Then
        mockMvc.perform(get("/posts")
                .param("page", String.valueOf(0))
                .param("size", String.valueOf(1))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-getAllWithPaging",
                        requestFields(),
                        responseFields(
                                fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("요청의 의해여, Api에서 전달할 데이터"),

                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("요청의 의해여, Api에서 전달할 데이터 리스트"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("요청한 데이터의 id"),
                                fieldWithPath("data.content[].title").type(JsonFieldType.STRING).description("요청한 게시물의 제목"),
                                fieldWithPath("data.content[].content").type(JsonFieldType.STRING).description("요청한 게시물의 내용"),
                                fieldWithPath("data.content[].createdBy").type(JsonFieldType.STRING).description("요청한 게시물의 작성자"),
                                fieldWithPath("data.content[].postType").type(JsonFieldType.STRING).description("요청한 게시물의 게시물 타입"),
                                fieldWithPath("data.content[].homeworkStatus").type(JsonFieldType.STRING).description("요청한 게시물이 과제게시물일 경우, 과제의 진행 상태를 의미 \n" +
                                        "(기본값은 None 이며, 진행상태의 종류는 NONE, ONGOING, COMPLETED, REJECTED 입니다.)").optional(),
                                fieldWithPath("data.content[].expireDate").type(JsonFieldType.STRING).description("요청한 게시물이 공지게시물일 경우, 공지 만료일을 의미 \n " +
                                        "(기본값은 1970년 1월 1일 정오입니다.)").optional(),
                                fieldWithPath("data.content[].postScope").type(JsonFieldType.STRING).description("요청한 게시물이 질문게시물일 경우, 연람 범위를 의미 \n" +
                                        "(기본값은 NONE 이며, PUBLIC 은 학생과 선생님이 열람가능하고, PRIVATE 는 선생님만 연람 가능합니다.)").optional(),

                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description(""),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("정렬상태"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("한 페이지에서 나타내는 게시글의 수"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 수"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 게시물의 수"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description(""),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description(""),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description(""),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description(""),

                                fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("Api에서 응답한 시각")
                        )
                    )
                );
    }

    @Test
    @DisplayName("특정 게시물의 정보를 요청하여, 전달받을 수 있다.")
    void getByIdTest() throws Exception {
        //Given
        var dataOne = (Post)postRepository.findAll().get(0);

        //When, Then
        mockMvc.perform(get("/posts/{id}", dataOne.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-getById",
                                requestFields(),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("요청의 의해 Api에서 전달할 데이터"),
                                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("요청한 데이터의 id"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("요청한 게시물의 제목"),
                                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("요청한 게시물의 내용"),
                                        fieldWithPath("data.createdBy").type(JsonFieldType.STRING).description("요청한 게시물의 작성자"),
                                        fieldWithPath("data.postType").type(JsonFieldType.STRING).description("요청한 게시물의 게시물 타입"),
                                        fieldWithPath("data.homeworkStatus").type(JsonFieldType.STRING).description("요청한 게시물이 과제게시물일 경우, 과제의 진행 상태를 의미 \n" +
                                                "(기본값은 None 이며, 진행상태의 종류는 NONE, ONGOING, COMPLETED, REJECTED 입니다.)").optional(),
                                        fieldWithPath("data.expireDate").type(JsonFieldType.STRING).description("요청한 게시물이 공지게시물일 경우, 공지 만료일을 의미 \n " +
                                                "(기본값은 1970년 1월 1일 정오입니다.)").optional(),
                                        fieldWithPath("data.postScope").type(JsonFieldType.STRING).description("요청한 게시물이 질문게시물일 경우, 연람 범위를 의미 \n" +
                                                "(기본값은 NONE 이며, PUBLIC 은 학생과 선생님이 열람가능하고, PRIVATE 는 선생님만 연람 가능합니다.)").optional(),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("Api에서 응답한 시각")
                                )
                        )
                );
    }

    @Test
    @DisplayName("게시물을 저장할 수 있다.")
    void saveTest() throws Exception {
        //When, Then
        mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PostDto.builder()
                        .postType(PostType.INTRODUCE)
                        .content("사실 저는 개명하였습니다. 그래서 본명은...")
                        .title("잘 부탁드립니다. 홍길동 입니다.")
                        .createdBy(defaultUserName)
                        .password("1234")
                        .postScope(PostScope.PUBLIC)
                        .homeworkStatus(HomeworkStatus.ONGOING)
                        .expireDate(LocalDateTime.of(2021, 11, 11, 11, 00, 00))
                        .build())))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-save",
                                requestFields(
                                        fieldWithPath("postType").type(JsonFieldType.STRING).description("저장할 게시물의 타입"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("저장될 게시물의 내용"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("저장될 게시물의 제목"),
                                        fieldWithPath("createdBy").type(JsonFieldType.STRING).description("저장될 게시물의 작성자"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("저장될 게시물을 후추 수정을 위해 필요한 비밀번호"),
                                        fieldWithPath("homeworkStatus").type(JsonFieldType.STRING).description("과제 게시물을 저장할 경우에 사용할 데이터이며, 과제 진행 상태를 의미 \n" +
                                                "(기본값은 None 이며, 진행상태의 종류는 NONE, ONGOING, COMPLETED, REJECTED 입니다.)").optional(),
                                        fieldWithPath("expireDate").type(JsonFieldType.STRING).description("공지 게시물을 저장할 경우에 사용할 데이터이며, 공지 만료일을 의미 \n " +
                                                "(기본값은 1970년 1월 1일 정오입니다.)").optional(),
                                        fieldWithPath("postScope").type(JsonFieldType.STRING).description("질문 게시물을 저장할 경우에 사용할 데이터이며, 본 게시물 열람 범위를 의미 \n  " +
                                                "(기본값은 NONE 이며, PUBLIC 은 학생과 선생님이 열람가능하고, PRIVATE 는 선생님만 연람 가능합니다.)").optional()
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("요청의 의해 Api에서 전달할 데이터"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("Api에서 응답한 시각")
                                )
                        )
                );

    }

    @Test
    @DisplayName("특정 게시물의 정보를 수정할 수 있다.")
    void updateTest() throws Throwable {
        //Given
        var dataOne = (Post)postRepository.findAll().get(0);

        var postDto = postService.findById(dataOne.getId());
        postDto.setTitle("수정되었습니다.");
        postDto.setPassword("1234");
        postDto.setHomeworkStatus(HomeworkStatus.REJECTED);
        postDto.setExpireDate(LocalDateTime.of(2021, 11, 11, 11, 00, 00));
        postDto.setPostScope(PostScope.PUBLIC);

        //When, Then
        mockMvc.perform(post("/posts/{id}", dataOne.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("posts-update",
                                requestFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("수정할 데이터의 id"),
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("수정될  게시물의 제목"),
                                        fieldWithPath("content").type(JsonFieldType.STRING).description("수정될 게시물의 내용"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("수정될 게시물을 후추 수정을 위해 필요한 비밀번호"),
                                        fieldWithPath("createdBy").type(JsonFieldType.STRING).description("수정될 게시물의 작성자"),
                                        fieldWithPath("postType").type(JsonFieldType.STRING).description("수정될 게시물의 타입"),
                                        fieldWithPath("homeworkStatus").type(JsonFieldType.STRING).description("과제 게시물을 수정할 경우에 사용할 데이터이며, 과제 진행 상태를 의미합 \n" +
                                                "(기본값은 None 이며, 진행상태의 종류는 NONE, ONGOING, COMPLETED, REJECTED 입니다.)").optional(),
                                        fieldWithPath("expireDate").type(JsonFieldType.STRING).description("공지 게시물을 수정할 경우에 사용할 데이터이며, 공지 만료일을 의미 \n " +
                                                "(기본값은 1970년 1월 1일 정오입니다.)").optional(),
                                        fieldWithPath("postScope").type(JsonFieldType.STRING).description("질문 게시물을 수정할 경우에 사용할 데이터이며, 본 게시물 열람 범위를 의미 \n  " +
                                                "(기본값은 NONE 이며, PUBLIC 은 학생과 선생님이 열람가능하고, PRIVATE 는 선생님만 연람 가능합니다.)").optional()
                                ),
                                responseFields(
                                        fieldWithPath("statusCode").type(JsonFieldType.NUMBER).description("상태코드"),
                                        fieldWithPath("data").type(JsonFieldType.NUMBER).description("수정된 게시물의 아이디"),
                                        fieldWithPath("serverDatetime").type(JsonFieldType.STRING).description("Api에서 응답한 시각")
                                )
                        )
                );

    }

    @Test
    @DisplayName("해당 게시물의 비밀번호가 아니면, 게시물의 정보를 수정할 수 없다.")
    void updateTestForInvalid() throws Throwable {
        //Given
        var dataOne = (Post)postRepository.findAll().get(0);

        var postDto = postService.findById(dataOne.getId());
        postDto.setTitle("수정되었습니다.");
        postDto.setPassword("123467");
        postDto.setHomeworkStatus(HomeworkStatus.REJECTED);
        postDto.setExpireDate(LocalDateTime.of(2021, 11, 11, 11, 00, 00));
        postDto.setPostScope(PostScope.PUBLIC);

        //When, Then
        mockMvc.perform(post("/posts/{id}", dataOne.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value(500))
                .andDo(print());


    }

}