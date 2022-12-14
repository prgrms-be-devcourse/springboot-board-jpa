package com.prgrms.devcourse.springjpaboard.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false, length = 20)
	private String name;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "hobby", nullable = false)
	private String hobby;

	@Builder
	public User(String name, Integer age, String hobby) {
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}
}
