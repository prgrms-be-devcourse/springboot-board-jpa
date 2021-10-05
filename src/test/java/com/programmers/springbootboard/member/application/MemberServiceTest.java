package com.programmers.springbootboard.member.application;

import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Age;
import com.programmers.springbootboard.member.domain.vo.Email;
import com.programmers.springbootboard.member.domain.vo.Hobby;
import com.programmers.springbootboard.member.domain.vo.Name;
import com.programmers.springbootboard.member.dto.MemberDetailResponse;
import com.programmers.springbootboard.member.dto.MemberSignRequest;
import com.programmers.springbootboard.member.dto.MemberUpdateRequest;
import com.programmers.springbootboard.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberConverter memberConverter;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    @DisplayName("초기화")
    void init() {
        memberRepository.deleteAll();
    }

    @DisplayName("사용자_추가")
    @ParameterizedTest
    @CsvSource({
            "wrjs@naver.com,김동건,18,취미는 없습니다.",
            "wrj@naver.com,성민수,25,취미는 영어회화입니다",
            "smjdhappy@naver.com,이희찬,32,코딩이 저의 취미입니다."
    })
    void insert(String inputEmail, String inputName, String inputAge, String inputHobby) {
        // given
        Email email = new Email(inputEmail);
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);

        // when
        memberService.insert(request);

        // then
        long count = memberRepository.count();
        assertThat(1L).isEqualTo(count);
    }


    @DisplayName("사용자_삭제")
    @ParameterizedTest
    @CsvSource({
            "wrjs@naver.com,김동건,18,취미는 없습니다.",
            "wrj@naver.com,성민수,25,취미는 영어회화입니다",
            "smjdhappy@naver.com,이희찬,32,코딩이 저의 취미입니다."
    })
    void delete(String inputEmail, String inputName, String inputAge, String inputHobby) {
        //given
        Email email = new Email(inputEmail);
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);
        memberService.insert(request);

        List<Member> all = memberRepository.findAll();
        Email findEmail = all.get(0).getEmail();

        // when
        memberService.delete(findEmail);

        // then
        long count = memberRepository.count();
        assertThat(0L).isEqualTo(count);
    }

    @DisplayName("사용자_수정")
    @ParameterizedTest
    @CsvSource({
            "wrjs@naver.com,김동건,18,취미는 없습니다.",
            "wrj@naver.com,성민수,25,취미는 영어회화입니다",
            "smjdhappy@naver.com,이희찬,32,코딩이 저의 취미입니다."
    })
    void update(String inputEmail, String inputName, String inputAge, String inputHobby) {
        //given
        Email email = new Email(inputEmail);
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);
        memberService.insert(request);

        Name updatedName = new Name("강동진");
        Age updatedAge = new Age("20");
        Hobby updatedHobby = new Hobby("없어요 그런거");

        MemberUpdateRequest memberUpdateRequest = memberConverter.toMemberUpdateRequest(email, updatedName, updatedAge, updatedHobby);

        // when
        MemberDetailResponse update = memberService.update(memberUpdateRequest);

        // then
        assertThat(updatedName.getName()).isEqualTo(update.getName());
        assertThat(updatedAge.toString()).isEqualTo(update.getAge());
        assertThat(updatedHobby.getHobby()).isEqualTo(update.getHobby());
    }

    @DisplayName("사용자_조회")
    @ParameterizedTest
    @CsvSource({
            "wrjs@naver.com,김동건,18,취미는 없습니다.",
            "wrj@naver.com,성민수,25,취미는 영어회화입니다",
            "smjdhappy@naver.com,이희찬,32,코딩이 저의 취미입니다."
    })
    void member(String inputEmail, String inputName, String inputAge, String inputHobby) {
        //given
        Email email = new Email(inputEmail);
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);
        memberService.insert(request);

        List<Member> all = memberRepository.findAll();
        Email findEmail = all.get(0).getEmail();

        // when
        MemberDetailResponse memberDetailResponse = memberService.member(findEmail);

        // then
        assertThat(name.getName()).isEqualTo(memberDetailResponse.getName());
        assertThat(age.toString()).isEqualTo(memberDetailResponse.getAge());
        assertThat(hobby.getHobby()
        ).isEqualTo(memberDetailResponse.getHobby());
    }

    @DisplayName("다수_사용자_수정")
    @Test
    void members() {
        // given
        for (int i = 0; i < 3; i++) {
            Email email = new Email("wrjs" + i + "@naver.com");
            Name name = new Name("김동건");
            Age age = new Age("30");
            Hobby hobby = new Hobby("취미생활이다.");
            MemberSignRequest request = memberConverter.toMemberSignRequest(email, name, age, hobby);
            memberService.insert(request);
        }

        // when
        List<MemberDetailResponse> members = memberService.members();
        int count = members.size();

        // then
        assertThat(3).isEqualTo(count);
    }

}