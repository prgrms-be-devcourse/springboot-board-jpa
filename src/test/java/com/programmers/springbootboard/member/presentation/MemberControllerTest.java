package com.programmers.springbootboard.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// TODO :: 목객체로 테스트 진행, 현재 테스트 코드는 잘못되어있습니다.
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberConverter memberConverter;

    @BeforeEach
    void init() {
        // given
        memberService.deleteAll();
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("김동건");
        Age age = new Age("25");
        Hobby hobby = new Hobby("취미는 코딩입니다.");

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when
        memberService.insert(request);

        // then
        long count = memberService.count();
        assertThat(1L).isEqualTo(count);
    }

    @Test
    @DisplayName("회원가입")
    void insertMember() throws Exception {
        // given
        Email email = new Email("smjdhappy@naver.com");
        Name name = new Name("성민수");
        Age age = new Age("25");
        Hobby hobby = new Hobby("취미는 코딩입니다.");

        MemberSignRequest memberSignRequest = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when // then
        mockMvc.perform(post("/api/member")
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberSignRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-insert",
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("age").type(JsonFieldType.STRING).description("age"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("hobby")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.STRING).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
                        )
                ));
    }

    @Test
    @DisplayName("회원삭제")
    void deleteMember() throws Exception {
        // given
        Long id = 1L;

        // when // then
        mockMvc.perform(delete("/api/member/{id}", id)
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-delete",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("status"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("message"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
                        )
                ));
    }

    @Test
    @DisplayName("회원수정")
    void updateMember() throws Exception {
        // given
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("성민수");
        Age age = new Age("25");
        Hobby hobby = new Hobby("취미는 코딩입니다.");

        MemberUpdateRequest memberUpdateRequest = memberConverter.toMemberUpdateRequest(email, name, age, hobby);

        Long id = 1L;

        // when // then
        mockMvc.perform(patch("/api/member/{id}", id)
                        .contentType(MediaTypes.HAL_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(memberUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-update",
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("age").type(JsonFieldType.STRING).description("나이"),
                                fieldWithPath("hobby").type(JsonFieldType.STRING).description("취미")
                        ),
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.STRING).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
                        )
                ));
    }

    @Test
    @DisplayName("회원단건조회")
    void inquiryMember() throws Exception {
        // given
        Long id = 1L;

        // when // then
        mockMvc.perform(get("/api/member/{id}", id)
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("member-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("data"),
                                fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("email"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("name"),
                                fieldWithPath("data.age").type(JsonFieldType.STRING).description("age"),
                                fieldWithPath("data.hobby").type(JsonFieldType.STRING).description("hobby"),
                                fieldWithPath("link[]").type(JsonFieldType.ARRAY).description("hateoas"),
                                fieldWithPath("link[].rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link[].href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link[].type").type(JsonFieldType.STRING).description("HTTP Method Type")
                        )
                ));
    }

    @Test
    @DisplayName("회원전체조회")
    void inquiryMembers() throws Exception {
        // when // then
        mockMvc.perform(get("/api/member")
                        .contentType(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("members-inquiry",
                        responseFields(
                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("상태코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메세지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터"),
                                fieldWithPath("data.content[]").type(JsonFieldType.ARRAY).description("본문"),
                                fieldWithPath("data.content[].id").type(JsonFieldType.NUMBER).description("id"),
                                fieldWithPath("data.content[].email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.content[].name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("data.content[].age").type(JsonFieldType.STRING).description("나이"),
                                fieldWithPath("data.content[].hobby").type(JsonFieldType.STRING).description("취미"),
                                fieldWithPath("data.pageable").type(JsonFieldType.OBJECT).description("pageable"),
                                fieldWithPath("data.pageable.sort").type(JsonFieldType.OBJECT).description("pageable.sort"),
                                fieldWithPath("data.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.sorted"),
                                fieldWithPath("data.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("pageable.sort.unsorted"),
                                fieldWithPath("data.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("pageable.sort.empty"),
                                fieldWithPath("data.pageable.pageSize").type(JsonFieldType.NUMBER).description("pageable.pageSize"),
                                fieldWithPath("data.pageable.pageNumber").type(JsonFieldType.NUMBER).description("pageable.pageNumber"),
                                fieldWithPath("data.pageable.offset").type(JsonFieldType.NUMBER).description("pageable.offset"),
                                fieldWithPath("data.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("pageable.unpaged"),
                                fieldWithPath("data.pageable.paged").type(JsonFieldType.BOOLEAN).description("pageable.paged"),
                                fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("last"),
                                fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("totalElements"),
                                fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("totalPages"),
                                fieldWithPath("data.first").type(JsonFieldType.BOOLEAN).description("first"),
                                fieldWithPath("data.numberOfElements").type(JsonFieldType.NUMBER).description("numberOfElements"),
                                fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("size"),
                                fieldWithPath("data.number").type(JsonFieldType.NUMBER).description("number"),
                                fieldWithPath("data.sort").type(JsonFieldType.OBJECT).description("sort"),
                                fieldWithPath("data.sort.sorted").type(JsonFieldType.BOOLEAN).description("sort.sorted"),
                                fieldWithPath("data.sort.unsorted").type(JsonFieldType.BOOLEAN).description("sort.unsorted"),
                                fieldWithPath("data.sort.empty").type(JsonFieldType.BOOLEAN).description("sort.empty"),
                                fieldWithPath("data.empty").type(JsonFieldType.BOOLEAN).description("empty"),
                                fieldWithPath("link").type(JsonFieldType.OBJECT).description("hateoas"),
                                fieldWithPath("link.rel").type(JsonFieldType.STRING).description("title"),
                                fieldWithPath("link.href").type(JsonFieldType.STRING).description("href"),
                                fieldWithPath("link.type").type(JsonFieldType.STRING).description("HTTP Method Type")
                        )
                ));
    }

}