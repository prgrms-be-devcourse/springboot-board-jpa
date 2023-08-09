package com.seungwon.board.post.domain;

import java.text.MessageFormat;

import com.seungwon.board.common.BaseEntity;
import com.seungwon.board.common.exception.InvalidDataException;
import com.seungwon.board.common.exception.InvalidRequestException;
import com.seungwon.board.member.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
	private static final int TITLE_MAX_LENGTH = 100;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	@Column(nullable = false, length = TITLE_MAX_LENGTH)
	String title;

	@Column(nullable = false)
	@Lob
	String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id")
	Member writer;

	@Builder
	public Post(String title, String content, Member writer) {
		validateTitle(title);
		this.title = title;
		this.content = content;
		this.writer = writer;
		createdBy = writer.getName();
	}

	private void validateTitle(String title) {
		if (title.length() > TITLE_MAX_LENGTH || title.isBlank()) {
			throw new InvalidDataException(
					MessageFormat.format("입력된 제목={0}자. 제목은 최대 {1}자 입력 가능합니다.", title.length(), TITLE_MAX_LENGTH));
		}
	}

	public void modify(@NonNull String title, String content, Long updater) {
		Long initialWriter = this.writer.getId();
		if (updater != initialWriter) {
			throw new InvalidRequestException(
					MessageFormat.format("작성자는 변경 불가능합니다[기존 작성자 id={0}, 요청된 작성자 id={1}] ", initialWriter, updater)
			);
		}
		this.title = title;
		this.content = content;
	}
}
