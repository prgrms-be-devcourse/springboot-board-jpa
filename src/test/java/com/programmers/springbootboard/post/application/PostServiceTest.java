package com.programmers.springbootboard.post.application;

import com.programmers.springbootboard.member.application.MemberService;
import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.request.MemberSignRequest;
import com.programmers.springbootboard.post.converter.PostConverter;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import com.programmers.springbootboard.post.dto.response.PostDetailResponse;
import com.programmers.springbootboard.post.dto.response.PostInsertResponse;
import com.programmers.springbootboard.post.dto.request.PostUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;


// TODO :: 목객체로 테스트 진행, 현재 테스트 코드는 잘못되어있습니다.
@SpringBootTest
class PostServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberConverter memberConverter;

    @Autowired
    private PostService postService;

    @Autowired
    private PostConverter postConverter;

    @DisplayName("초기화")
    @BeforeEach
    void init() {
        memberService.deleteAll();
        Email email = new Email("wrjs@naver.com");
        Name name = new Name("김동건");
        Age age = new Age("24");
        Hobby hobby = new Hobby("행복하자~~행복하자~");

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);
        memberService.insert(request);

        Title title = new Title("이것은 제목");
        Content content = new Content("이것은 본문이다!");

        PostInsertResponse postRequest = postConverter.toPostInsertRequest(email, title, content);

        postService.insert(email, postRequest);
    }

    @DisplayName("게시물_수정")
    @ParameterizedTest
    @CsvSource({
            "김동건이당,취미는 없습니다.,1",
            "성민수당당,취미는 영어회화입니다,2",
            "이희찬이당당,코딩이 저의 취미입니다.,3"
    })
    void update(String inputTitle, String inputContent, String inputId) {
        // given
        Email email = new Email("wrjs@naver.com");
        Long id = Long.parseLong(inputId);

        Title title = new Title(inputTitle);
        Content content = new Content(inputContent);

        PostUpdateRequest request = postConverter.toPostUpdateRequest(email, title, content);

        // when
        postService.update(email, id, request);

        // then
        PostDetailResponse post = postService.findById(id);

        assertAll("Test Post update",
                () -> assertEquals(title.getTitle(), post.getTitle(), "Fail To Get Title"),
                () -> assertEquals(content.getContent(), post.getContent(), "Fail To Get Title")
        );
    }

    @DisplayName("게시물_단건_출력")
    @Test
    void post() {
        PostDetailResponse post = postService.findById(1L);
        assertThat("wrjs@naver.com").isEqualTo(post.getEmail());
    }

    @DisplayName("다수_게시물_출력")
    @Test
    void posts() {
        assertThat(1).isEqualTo(postService.count());
    }

    @DisplayName("게시물_삭제")
    @Test
    void delete() {
        // given
        Long id = 1L;
        Email email = new Email("wrjs@naver.com");

        // when
        postService.deleteByEmail(id, email);

        // then
        assertThat(0L).isEqualTo(postService.count());
    }
}