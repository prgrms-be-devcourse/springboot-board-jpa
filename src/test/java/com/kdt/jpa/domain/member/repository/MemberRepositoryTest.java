package com.kdt.jpa.domain.member.repository;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.kdt.jpa.domain.member.model.Member;

@DataJpaTest
@EnableJpaRepositories(basePackageClasses = {MemberRepositoryTest.class})
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	TestEntityManager entityManager;

	@Test
	@DisplayName("한 글자 이름의 멤버는 생성이 불가능하다.")
	public void JoinWithALetterNameFail() {
		//given
		Member member = Member.builder()
			.name("a")
			.age(26)
			.hobby("개발")
			.build();

		//when
		memberRepository.save(member);

		//then
		Assertions.assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("25자 이상 이름의 멤버는 생성이 불가능하다.")
	public void JoinWithOverUpperBoundLengthNameFail() {
		//given
		Member member = Member.builder()
			.name("abcdefghijklmnopqrstuvwxyz")
			.age(26)
			.hobby("개발")
			.build();

		//when
		memberRepository.save(member);

		//then
		Assertions.assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("멤버 생성시 나이는 음수면 안된다.")
	public void JoinWithNegativeAgeFail() {
		//given
		Member member = Member.builder()
			.name("jinhyungPark")
			.age(-1)
			.hobby("개발")
			.build();

		//when
		memberRepository.save(member);

		//then
		Assertions.assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("멤버 생성시 나이는 0살이면 안된다.")
	public void JoinWithZeroAgeFail() {
		//given
		Member member = Member.builder()
			.name("jinhyungPark")
			.age(0)
			.hobby("개발")
			.build();

		//when
		memberRepository.save(member);

		//then
		Assertions.assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("17글자 이상의 취미는 불가능하다.")
	public void JoinWithOverUpperboundLengthHobbyFail() {
		//given
		Member member = Member.builder()
			.name("jinhyungPark")
			.age(27)
			.hobby("오늘같이비오는날에는책읽기랄까??")
			.build();

		//when
		memberRepository.save(member);

		//then
		Assertions.assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("16글자의 취미는 허용한다.")
	public void JoinWithValidLengthHobbySuccess() {
		//given
		Member member = Member.builder()
			.name("jinhyungPark")
			.age(27)
			.hobby("최대열여섯글자의취미를담을수있다")
			.build();

		//when
		Member joinedMember = memberRepository.save(member);

		//then
		Assertions.assertThat(joinedMember.getId()).isNotNull();
		Assertions.assertThat(joinedMember.getName()).isEqualTo(member.getName());
		Assertions.assertThat(joinedMember.getAge()).isEqualTo(member.getAge());
		Assertions.assertThat(joinedMember.getHobby()).isEqualTo(member.getHobby());
	}

	@Test
	@DisplayName("멤버 생성 성공 테스트")
	public void JoinSuccess() {
		//given
		Member member = Member.builder()
			.name("jinhyungPark")
			.age(26)
			.hobby("개발")
			.build();

		//when
		Member joinedMember = memberRepository.save(member);

		//then
		Assertions.assertThat(joinedMember.getId()).isNotNull();
		Assertions.assertThat(joinedMember.getName()).isEqualTo(member.getName());
		Assertions.assertThat(joinedMember.getAge()).isEqualTo(member.getAge());
		Assertions.assertThat(joinedMember.getHobby()).isEqualTo(member.getHobby());
	}
}
