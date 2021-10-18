package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "hobby")
	private String hobby;

	// TODO: 연관관계 맵핑 확인 테스트 코드 작성?
	@OneToMany(mappedBy = "user")
	private List<Post> posts = new ArrayList<>();

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return id.equals(user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
