package com.example.board.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	public void updateTitle(String newTitle) {
		this.title = newTitle;
	}

	public void updateContent(String newContent) {
		this.content = newContent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Post post = (Post) o;
		return id.equals(post.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
