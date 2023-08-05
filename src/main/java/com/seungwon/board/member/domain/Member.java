package com.seungwon.board.member.domain;

import java.text.MessageFormat;

import com.seungwon.board.common.BaseEntity;
import com.seungwon.board.common.exception.InvalidDataException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	private static final int NAME_MAX_LENGTH = 10;
	private static final int HOBBY_MAX_LENGTH = 100;
	private static final int MAX_AGE = 120;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(nullable = false, length = 10)
	String name;

	int age;

	@Column(nullable = false, length = 100)
	String hobby;

	@Builder
	public Member(String name, int age, String hobby) {
		validateName(name);
		validateAge(age);
		validateHobby(hobby);
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	private void validateName(String name) {
		boolean onlyContainsCharacters = name.chars().allMatch(Character::isLetter);
		if (!onlyContainsCharacters) {
			throw new InvalidDataException(
					MessageFormat.format("입력된 이름={0}. 이름에는 문자만 입력 가능합니다.", name));
		}
		if (name.length() > 10) {
			throw new InvalidDataException(
					MessageFormat.format("입력된 이름={0}자. 이름은 최대 {1}자 입력 가능합니다.", name.length(), NAME_MAX_LENGTH));
		}
	}

	void validateAge(int age) {
		if (!(0 < age && age <= MAX_AGE)) {
			throw new InvalidDataException(
					MessageFormat.format("입력된 나이={0}. 나이는 0 이상 {1} 이하의 숫자로 입력해주세요.", age, MAX_AGE));
		}
	}

	private void validateHobby(String hobby) {
		if (hobby.length() > 100) {
			throw new InvalidDataException(
					MessageFormat.format("입력된 취미={0}자. 취미는 최대 {1}자 입력 가능합니다.", hobby.length(), HOBBY_MAX_LENGTH));
		}
	}

}
