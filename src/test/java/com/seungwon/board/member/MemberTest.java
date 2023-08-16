package com.seungwon.board.member;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.seungwon.board.common.exception.InvalidDataException;
import com.seungwon.board.member.domain.Member;

class MemberTest {
	private static final int MAX_AGE = 120;

	@DisplayName("이름이 비어있거나 최대 길이 이상인 경우 예외가 발생한다")
	@ParameterizedTest
	@ValueSource(strings = {
			"",
			"가나다라마바사아자차카"
	})
	void name_length_validation(String name) {
		// given
		String validHobby = "운동";
		int validAge = 26;

		// when

		// then
		assertThrows(InvalidDataException.class, () -> new Member(name, validAge, validHobby));
	}

	@DisplayName("이름에 문자외에 숫자나 특수문자가 포함된다면 예외가 발생한다")
	@ParameterizedTest
	@ValueSource(strings = {
			"1234",
			"가#나$"
	})
	void name_character_validation(String name) {
		// given
		String validHobby = "운동";
		int validAge = 26;

		// when

		// then
		assertThrows(InvalidDataException.class, () -> new Member(name, validAge, validHobby));
	}

	@DisplayName("나이가 0 이하 또는 최대 연령 이상인 경우 예외가 발생한다")
	@ParameterizedTest
	@ValueSource(ints = {-1, 0, MAX_AGE + 1})
	void age_validation(int age) {
		// given
		String validHobby = "운동";
		String validName = "한승원";

		// when

		// then
		assertThrows(InvalidDataException.class, () -> new Member(validName, age, validHobby));
	}

	@DisplayName("입력된 취미가 비어있거나 최대 글자수 이상인 경우 예외가 발생한다")
	@ParameterizedTest
	@ValueSource(strings = {
			"",
			"사용자의 취미를 여기에 입력합니다. 취미에 최대 글자 개수 이상의 문자열이 입력되는 경우 테스트로 예외가 발생함을 검증하기 위해 입력하는 취미란에 입력될 수 있는 예시 문자열입니다."
	})
	void hobby_validation(String hobby) {
		// given
		String validName = "한승원";
		int validAge = 26;

		// when

		// then
		assertThrows(InvalidDataException.class, () -> new Member(validName, validAge, hobby));
	}
}
