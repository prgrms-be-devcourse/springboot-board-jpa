package com.programmers.springboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Column(name = "age", nullable = false)
	private int age;

	@Column(name = "hobby")
	private String hobby;

	public void updateMemberNameAndHobby(String name, String hobby) {
		this.name = name;
		this.hobby = hobby;
	}
}
