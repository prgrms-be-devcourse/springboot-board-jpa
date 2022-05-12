package org.prgms.board.domain.user.domain;

import static com.google.common.base.Preconditions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.prgms.board.domain.post.domain.Post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
	public static final int MAX_USER_NAME_LENGTH = 30;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(length = MAX_USER_NAME_LENGTH, nullable = false, unique = true)
	private String name;

	@Column(nullable = false)
	private int age;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> posts = new ArrayList<>();

	private LocalDateTime createdAt;

	private User(String name, int age) {
		validateInfo(name, age);

		this.name = name;
		this.age = age;
		this.createdAt = LocalDateTime.now();
	}

	private void validateInfo(String name, int age) {
		checkArgument(Objects.nonNull(name) && name.length() > 0 && name.length() <= MAX_USER_NAME_LENGTH,
			"이름의 길이는 1자 이상 10자 이하여야 합니다.");
		checkArgument(Objects.nonNull(age) && age > 0, "나이 항목은 필수입니다.");
	}

	public static User create(String name, int age) {
		return new User(name, age);
	}

	public void updateUser(String name, int age) {
		validateInfo(name, age);

		this.name = name;
		this.age = age;
	}

	// 얀관관계 편의 메서드 START

	public void setPost(Post post) {
		post.setUser(this);
	}

	// 연관관계 편의 메서드 END
}
