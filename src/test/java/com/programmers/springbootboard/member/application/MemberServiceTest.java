package com.programmers.springbootboard.member.application;

import com.programmers.springbootboard.member.converter.MemberConverter;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.member.domain.vo.Age;
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
            "김동건,18,취미는 없습니다.",
            "성민수,25,취미는 영어회화입니다",
            "이희찬,32,코딩이 저의 취미입니다."
    })
    void insert(String inputName, String inputAge, String inputHobby) {
        // given
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(name, age, hobby);

        // then
        memberService.insert(request);

        // when
        long count = memberRepository.count();
        assertThat(1L).isEqualTo(count);
    }


    @DisplayName("사용자_삭제")
    @ParameterizedTest
    @CsvSource({
            "김동건,18,취미는 없습니다.",
            "성민수,25,취미는 영어회화입니다",
            "이희찬,32,코딩이 저의 취미입니다."
    })
    void delete(String inputName, String inputAge, String inputHobby) {
        //given
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(name, age, hobby);
        memberService.insert(request);

        List<Member> all = memberRepository.findAll();
        Long id = all.get(0).getId();

        // then
        memberService.delete(id);

        // when
        long count = memberRepository.count();
        assertThat(0L).isEqualTo(count);
    }

    @DisplayName("사용자_수정")
    @ParameterizedTest
    @CsvSource({
            "김동건,18,취미는 없습니다.",
            "성민수,25,취미는 영어회화입니다",
            "이희찬,32,코딩이 저의 취미입니다."
    })
    void update(String inputName, String inputAge, String inputHobby) {
        //given
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(name, age, hobby);
        memberService.insert(request);

        List<Member> all = memberRepository.findAll();
        Long id = all.get(0).getId();

        Name updatedName = new Name("강동진");
        Age updatedAge = new Age("20");
        Hobby updatedHobby = new Hobby("없어요 그런거");

        MemberUpdateRequest memberUpdateRequest = memberConverter.toMemberUpdateRequest(updatedName, updatedAge, updatedHobby);

        // then
        MemberDetailResponse update = memberService.update(id, memberUpdateRequest);

        // when
        assertThat(updatedName).isEqualTo(update.getName());
        assertThat(updatedAge).isEqualTo(update.getAge());
        assertThat(updatedHobby).isEqualTo(update.getHobby());
    }

    @DisplayName("사용자_조회")
    @ParameterizedTest
    @CsvSource({
            "김동건,18,취미는 없습니다.",
            "성민수,25,취미는 영어회화입니다",
            "이희찬,32,코딩이 저의 취미입니다."
    })
    void member(String inputName, String inputAge, String inputHobby) {
        //given
        Name name = new Name(inputName);
        Age age = new Age(inputAge);
        Hobby hobby = new Hobby(inputHobby);

        MemberSignRequest request = memberConverter.toMemberSignRequest(name, age, hobby);
        memberService.insert(request);

        List<Member> all = memberRepository.findAll();
        Long id = all.get(0).getId();

        // then
        MemberDetailResponse memberDetailResponse = memberService.member(id);

        // when
        assertThat(name).isEqualTo(memberDetailResponse.getName());
        assertThat(age).isEqualTo(memberDetailResponse.getAge());
        assertThat(hobby).isEqualTo(memberDetailResponse.getHobby());
    }

    @DisplayName("다수_사용자_수정")
    @Test
    void members() {
        // given
        for (int i = 0; i < 3; i++) {
            Name name = new Name("김동건");
            Age age = new Age("30");
            Hobby hobby = new Hobby("취미생활이다.");
            MemberSignRequest request = memberConverter.toMemberSignRequest(name, age, hobby);
            memberService.insert(request);
        }

        // then
        List<MemberDetailResponse> members = memberService.members();
        int count = members.size();

        // when
        assertThat(3).isEqualTo(count);
    }

}