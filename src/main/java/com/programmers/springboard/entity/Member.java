package com.programmers.springboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Column(name = "login_id", nullable = false)
	private String loginId;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private Role role;

	public Member(String name, String loginId, String password) {
		this.name = name;
		this.loginId = loginId;
		this.password = password;
		this.role = Role.ROLE_USER;
	}

	public void changePassword(String password) {
		this.password = password;
	}
}
