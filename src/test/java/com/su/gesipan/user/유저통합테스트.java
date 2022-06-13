package com.su.gesipan.user;

import com.su.gesipan.document.USER_DOC;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.su.gesipan.helper.Helper.toJson;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class 유저통합테스트 {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void 유저를_생성할_수_있다() throws Exception {
        var request = USER_DOC.createField();
        var response = USER_DOC.resultField();

        var dto = UserDto.Create.of("su", 24L, "게임");
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))

                .andExpect(status().isCreated())
                .andExpect(handler().methodName("createUser"))

                .andDo(print())
                .andDo(document("user-create", request, response));
    }

    @Nested
    class CreateDto_Validation {

        @Test
        void 이름은_20자_이내() throws Exception {
            var illegalName = RandomString.make(21);
            var dto = UserDto.Create.of(illegalName, 24L, "취미");
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))

                    .andExpect(status().isBadRequest())

                    .andDo(print());
        }

        @Test
        void 나이는_양수() throws Exception {
            var illegalAge = -1L;
            var dto = UserDto.Create.of("su", illegalAge, "취미");
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))

                    .andExpect(status().isBadRequest())

                    .andDo(print());
        }

        @Test
        void 취미는_50자_이내() throws Exception {
            var illegalHobby = RandomString.make(51);
            var dto = UserDto.Create.of("su", 24L, illegalHobby);
            mockMvc.perform(post("/api/v1/users")
                            .contentType(MediaType.APPLICATION_JSON).content(toJson(dto)))

                    .andExpect(status().isBadRequest())

                    .andDo(print());

        }
    }

}
