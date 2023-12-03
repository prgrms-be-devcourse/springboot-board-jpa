package com.programmers.springboard.member.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.programmers.springboard.member.exception.LoginFailException;
import com.programmers.springboard.global.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE member SET is_deleted = true WHERE id = ?")
@Where(clause = "is_deleted = FALSE")
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false, length = 30)
	private String name;

	@Column(name = "login_id", nullable = false)
	private String loginId;

	@Column(name = "password")
	private String password;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "last_login_date")
	private LocalDateTime lastLoginDate;

	@Column(name = "is_deleted")
	private Boolean isDeleted = false;

	@Column(name = "is_activated")
	private Boolean isActivated = true;

	public Member(String name, String loginId, String password) {
		this.name = name;
		this.loginId = loginId;
		this.password = password;
		this.role = Role.ROLE_USER;
		this.lastLoginDate = LocalDateTime.now();
	}

	public void changePassword(String password) {
		this.password = password;
	}

	public List<GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
		if (!passwordEncoder.matches(credentials, this.password)) {
			throw new LoginFailException();
		}
	}

	public void updateLastLoginDate() {
		this.lastLoginDate = LocalDateTime.now();
	}

	public void deactivate() {
		this.isActivated = false;
	}

}
