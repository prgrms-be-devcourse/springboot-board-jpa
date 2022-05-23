package com.prgrms.boardjpa.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.util.Assert;

import com.prgrms.boardjpa.commons.domain.BaseEntity;

import lombok.Getter;

@Getter
@Entity
public class Board extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	protected Board() {
	}

	private Board(String title, String content) {
		validateTitle(title);
		validateContent(content);

		this.title = title;
		this.content = content;
	}

	public static BoardBuilder builder() {
		return new BoardBuilder();
	}

	public static class BoardBuilder {
		private String title;
		private String content;

		private BoardBuilder() {
		}

		public BoardBuilder title(String title) {
			this.title =title;
			return this;
		}

		public BoardBuilder content(String content) {
			this.content = content;
			return this;
		}

		public Board build() {
			return new Board(this.title, this.content);
		}
	}

	public void edit(String title, String content) {
		validateTitle(title);
		validateContent(content);

		this.title = title;
		this.content = content;
	}

	private void validateTitle(String title) {
		Assert.hasText(title, "제목을 입력해주세요");
	}

	private void validateContent(String content) {
		Assert.hasText(content, "본문을 입력해주세요");
	}
}
