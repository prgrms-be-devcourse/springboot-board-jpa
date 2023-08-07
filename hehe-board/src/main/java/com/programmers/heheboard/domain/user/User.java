package com.programmers.heheboard.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.programmers.heheboard.domain.post.Post;
import com.programmers.heheboard.global.BaseEntity;
import com.programmers.heheboard.global.codes.ErrorCode;
import com.programmers.heheboard.global.exception.GlobalRuntimeException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

	private static final int MIN_AGE = 0;
	private static final int MAX_AGE = 100;
	private static final String NAME_REG_EXP = "[a-zA-Z0-9가-힣]{2,10}";
	private static final String HOBBY_REG_EXP = "[가-힣]{2,10}";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 10, nullable = false)
	private String name;
	@Column(nullable = false)
	private int age;
	@Column(nullable = false)
	private String hobby;
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private final List<Post> posts = new ArrayList<>();

	@Builder
	public User(String name, int age, String hobby) {
		validateAge(age);
		validateName(name);
		validateHobby(hobby);
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

	public void addPost(Post post) {
		posts.add(post);
		post.attachUser(this);
	}

	public void removePost(Post post) {
		this.posts.remove(post);
	}

	private void validateAge(int age) {
		if (age < MIN_AGE || age > MAX_AGE) {
			throw new GlobalRuntimeException(ErrorCode.USER_AGE_VALIDATION_FAIL);
		}
	}

	private void validateName(String name) {
		if (!Pattern.matches(NAME_REG_EXP, name)) {
			throw new GlobalRuntimeException(ErrorCode.USER_AGE_VALIDATION_FAIL);
		}
	}

	private void validateHobby(String hobby) {
		if (!Pattern.matches(HOBBY_REG_EXP, hobby)) {
			throw new GlobalRuntimeException(ErrorCode.USER_HOBBY_VALIDATION_FAIL);
		}
	}
}
