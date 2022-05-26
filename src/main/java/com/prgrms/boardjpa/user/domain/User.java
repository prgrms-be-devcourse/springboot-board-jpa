package com.prgrms.boardjpa.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.Assert;

import com.prgrms.boardjpa.commons.exception.CreationFailException;
import com.prgrms.boardjpa.user.exception.AuthenticationFailException;

import lombok.Getter;

@Getter
@Entity
public class User {
	private static final int MIN_NAME_LENGTH = 2;
	private static final int MIN_AGE = 15;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Enumerated(EnumType.STRING)
	@Column(name = "hobby")
	private Hobby hobby;
	// TODO : nullable = true 라면 getter 에서는 Optional 을 반환해줘야 할까? getter 가 Optional 을 반환하는 것은 바람직한가?

	@Column(name = "password", nullable = false)
	private String password;

	protected User() {
	}

	private User(String name, int age, String email, Hobby hobby, String password) {
		validateName(name);
		validateAge(age);
		validateEmail(email);
		validatePassword(password);

		this.name = name;
		this.age = age;
		this.email = email;
		this.hobby = hobby;
		this.password = password;
	}

	public void login(String inputPassword) {
		this.matchPassword(inputPassword);
	}

	private void matchPassword(String inputPassword) {
		if (!this.password.equals(inputPassword)) {
			throw new AuthenticationFailException();
		}
	}

	public void changeEmail(String email) {
		validateEmail(email);

		this.email = email;
	}

	public void changeName(String name) {
		validateName(name);

		this.name = name;
	}

	public void changeHobby(Hobby hobby) {
		this.hobby = hobby;
	}

	private void validateName(String name) {
		Assert.hasText(name, "닉네임은 2글자 이상이어야 합니다");

		if (name.length() < MIN_NAME_LENGTH) {
			throw new IllegalArgumentException("닉네임은 2글자 이상이어야 합니다");
		}
	}

	private void validateAge(int age) {
		if (age < MIN_AGE) {
			throw new IllegalArgumentException("15세 이상이어야 합니다");
		}
	}

	private void validatePassword(String password) {
		Assert.hasText(password, "비밀번호는 비어있을 수 없습니다");
	}

	private void validateEmail(String email) {
		Assert.hasText(email, "이메일은 비어있을 수 없습니다");
	}

	public static UserBuilder builder() {
		return new UserBuilder();
	}

	public static class UserBuilder {
		private String name;
		private Integer age;
		private String email;
		private Hobby hobby;
		private String password;

		private UserBuilder() {
		}

		public UserBuilder name(String name) {
			this.name = name;
			return this;
		}

		public UserBuilder age(int age) {
			this.age = age;
			return this;
		}

		public UserBuilder hobby(Hobby hobby) {
			this.hobby = hobby;
			return this;
		}

		public UserBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder password(String password) {
			this.password = password;
			return this;
		}

		public User build() {
			try {
				return new User(name, age, email, hobby, password);
			} catch (IllegalArgumentException e) {
				throw new CreationFailException(User.class);
			}
		}
	}

}

