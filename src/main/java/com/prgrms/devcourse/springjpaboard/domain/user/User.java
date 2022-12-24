package com.prgrms.devcourse.springjpaboard.domain.user;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.StringUtils;

import com.google.common.base.Preconditions;
import com.prgrms.devcourse.springjpaboard.global.common.base.BaseEntity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class User extends BaseEntity {

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
		Preconditions.checkArgument(StringUtils.hasText(name));
		Preconditions.checkArgument(name.length() <= 20);
		Preconditions.checkArgument(Objects.nonNull(age));
		Preconditions.checkArgument(age >= 0);
		Preconditions.checkArgument(StringUtils.hasText(hobby));
		this.name = name;
		this.age = age;
		this.hobby = hobby;
	}

}
