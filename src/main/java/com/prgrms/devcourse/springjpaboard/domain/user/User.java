package com.prgrms.devcourse.springjpaboard.domain.user;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		User user = (User)o;
		return getId().equals(user.getId()) && getName().equals(user.getName()) && getAge().equals(user.getAge())
			&& getHobby().equals(user.getHobby());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getAge(), getHobby());
	}
}
