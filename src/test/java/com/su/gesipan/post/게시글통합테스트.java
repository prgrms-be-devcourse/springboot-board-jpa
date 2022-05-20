package com.su.gesipan.post;

import com.su.gesipan.document.POST_DOC;
import com.su.gesipan.user.User;
import com.su.gesipan.user.UserRepository;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.su.gesipan.helper.Helper.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestBody;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseBody;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class 게시글통합테스트 {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;

    @Test
    void 게시글_생성() throws Exception {
        var request = POST_DOC.create();
        var response = POST_DOC.result();

        var user = userRepository.save(User.of("su", 24L, "게임"));
        var dto = PostDto.Create.of(user.getId(), "제목", "본문");

        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))
                .andExpect(handler().methodName("createPost"))
                .andExpect(status().isCreated())

                .andDo(print())
                .andDo(document("post-create", request, response));
    }

    @Nested
    class 게시글_조회 {
        User user;
        Post post;

        @BeforeEach
        @Transactional
        void USER_AND_POST_SETUP() {
            user = User.of("su", 24L, "게임");
            post = Post.of("제목", "본문");
            user.addPost(post);
            userRepository.save(user);
        }

        @Test
        void 페이징() throws Exception {

            mockMvc.perform(get("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("page", "0")
                            .param("size", "5"))
                    .andExpect(status().isOk())
                    .andExpect(handler().methodName("findAll"))

                    .andDo(print())
                    .andDo(document("post-findAll", requestBody(), responseBody()));
        }

        @Test
        void 단건() throws Exception {
            var response = POST_DOC.result();

            mockMvc.perform(get("/api/v1/posts/{id}", post.getId()))
                    .andExpect(status().isOk())

                    .andDo(print())
                    .andDo(document("post-findById", requestBody(), response));
        }
    }

    @Nested
    class 게시글_수정 {
        User user;
        Post post;

        @BeforeEach
        @Transactional
        void USER_AND_POST_SETUP() {
            user = User.of("su", 24L, "게임");
            post = Post.of("제목", "본문");
            user.addPost(post);
            userRepository.save(user);
        }

        @Test
        void 수정가능() throws Exception {
            var request = POST_DOC.update();
            var updateDto = PostDto.Update.of("수정제목", "수정본문");

            mockMvc.perform(post("/api/v1/posts/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(updateDto)))

                    .andExpect(handler().methodName("editPost"))
                    .andExpect(status().isOk())

                    .andDo(print())
                    .andDo(document("post-update", request, responseBody()));
        }
    }

    @Nested
    class CreateDto_Validation {

        @Test
        void userId_는_NotNull() throws Exception {
            var dto = PostDto.Create.of(null, "제목", "본문");

            mockMvc.perform(post("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        void 게시글_제목은_1글자_이상() throws Exception {
            var illegalTitle = "";
            var dto = PostDto.Create.of(1L, illegalTitle, "본문");

            mockMvc.perform(post("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        void 게시글_제목은_100글자_이내() throws Exception {
            var illegalTitle = RandomString.make(101);
            var dto = PostDto.Create.of(1L, illegalTitle, "본문");

            mockMvc.perform(post("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        void 게시글_본문은_1500글자_이내() throws Exception {
            var illegalContent = RandomString.make(1501);
            var dto = PostDto.Create.of(1L, "제목", illegalContent);

            mockMvc.perform(post("/api/v1/posts")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

    @Nested
    class UpdateDto_Validation {
        User user;
        Post post;

        @BeforeEach
        @Transactional
        void USER_AND_POST_SETUP() {
            user = User.of("su", 24L, "게임");
            post = Post.of("제목", "본문");
            user.addPost(post);
        }

        @Test
        void 게시글_제목은_1글자_이상() throws Exception {
            var illegalTitle = "";
            var updateDto = PostDto.Update.of(illegalTitle, "수정본문");

            mockMvc.perform(post("/api/v1/posts/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(updateDto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        void 게시글_제목은_100글자_이내() throws Exception {
            var illegalTitle = RandomString.make(101);
            var updateDto = PostDto.Update.of(illegalTitle, "수정본문");

            mockMvc.perform(post("/api/v1/posts/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(updateDto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }

        @Test
        void 게시글_본문은_1500자_이내() throws Exception {
            var illegalContent = RandomString.make(101);
            var updateDto = PostDto.Update.of("수정제목", illegalContent);

            mockMvc.perform(post("/api/v1/posts/{id}", post.getId())
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(updateDto)))
                    .andExpect(status().isBadRequest())
                    .andDo(print());
        }
    }

}
