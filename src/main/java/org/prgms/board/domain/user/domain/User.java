package org.prgms.board.domain.user.domain;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.prgms.board.domain.post.domain.Post;
import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** 사용자 **/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	public static final int MAX_USER_NAME_LENGTH = 30;

	/** 사용자 식별 번호 **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long id;

	/** 사용자 이름 **/
	@Column(length = MAX_USER_NAME_LENGTH, nullable = false, unique = true)
	private String name;

	/** 사용자 나이 **/
	@Column(nullable = false)
	private int age;

	/** 사용자 게시글 목록**/
	@OneToMany(mappedBy = "writer")
	private List<Post> posts = new ArrayList<>();

	/** 사용자 생성 일자 **/
	private LocalDateTime createdAt;

	private User(String name, int age) {
		validateUserInfo(name, age);

		this.name = name;
		this.age = age;
		this.createdAt = LocalDateTime.now();
	}

	private void validateUserInfo(String name, Integer age) {
		checkArgument(StringUtils.hasText(name) && name.length() <= MAX_USER_NAME_LENGTH,
			"이름의 길이는 1자 이상 10자 이하여야 합니다.");
		checkArgument(Objects.nonNull(age) && age > 0, "나이 항목은 필수입니다.");
	}

	public static User create(String name, int age) {
		return new User(name, age);
	}

	public void updateUser(String name, int age) {
		validateUserInfo(name, age);

		this.name = name;
		this.age = age;
	}

}
