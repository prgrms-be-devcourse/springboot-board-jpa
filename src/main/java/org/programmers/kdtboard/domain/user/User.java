package org.programmers.kdtboard.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.programmers.kdtboard.controller.response.ErrorCode;
import org.programmers.kdtboard.domain.BaseEntity;
import org.programmers.kdtboard.exception.NotValidException;

import lombok.Builder;

@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(nullable = false, length = 10)
	private String name;

	@Column(nullable = false)
	private int age;

	@Column(length = 30)
	private String hobby;

	protected User() {
	}

	@Builder
	public User(Long id, String name, int age, String hobby) {
		verifyName(name);
		verifyAge(age);
		verifyHobby(hobby);

		this.id = id;
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	private void verifyAge(int age) {
		if (age < 0) {
			throw new NotValidException(ErrorCode.INVALID_REQUEST_VALUE, "나이는 음수일 수 없습니다.");
		}
	}

	private void verifyName(String name) {
		if (name.isBlank() || name.length() > 10) {
			throw new NotValidException(ErrorCode.INVALID_REQUEST_VALUE, "이름의 길이는 1 이상 10 이하입니다.");
		}
	}

	private void verifyHobby(String hobby) {
		if (hobby.length() > 30) {
			throw new NotValidException(ErrorCode.INVALID_REQUEST_VALUE, "취미의 길이는 30을 초과할 수 없습니다.");
		}
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getHobby() {
		return hobby;
	}
}
