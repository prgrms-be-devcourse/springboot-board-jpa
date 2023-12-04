package com.programmers.springboard.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String loginId;

	private String passwd;

	private String name;

	private Integer age;

	private String hobby;

	private LocalDateTime loginAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private Groups groups;

	public void checkPassword(PasswordEncoder passwordEncoder, String credentials){
		if(!passwordEncoder.matches(credentials, passwd)){
			throw new IllegalArgumentException("Bad credentials");
		}
	}

    public void changeGroup(Groups groups) {
		this.groups = groups;
    }

	public void changeLoginAt(){
		loginAt = LocalDateTime.now();
	}
}
