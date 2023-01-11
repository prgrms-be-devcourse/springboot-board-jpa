package com.example.board.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.board.domain.user.validator.UserValidator;
import com.example.board.domain.userhobby.entity.UserHobby;
import com.example.board.global.entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

	private static final UserValidator validator = new UserValidator();

	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int age;

	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<UserHobby> userHobbies = new ArrayList<>();

	@Builder
	public User(String name, int age) {
		validator.validateName(name);
		validator.validateAge(age);
		this.name = name;
		this.age = age;
	}

	public void addHobby(UserHobby userHobby) {
		userHobbies.add(userHobby);
		userHobby.targetUser(this);
	}
}
