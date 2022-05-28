package org.prgrms.board.web.post;

import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.board.domain.post.Post;

public class PostDto {

	private final Long id;
	private final String title;
	private final String content;
	private final LocalDateTime createdAt;
	private final WriterDto writerDto;

	public PostDto(Post post) {
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
		this.createdAt = post.getCreatedAt();
		this.writerDto = new WriterDto(post.getWriter());
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public WriterDto getWriterDto() {
		return writerDto;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("title", title)
			.append("content", content)
			.append("createdAt", createdAt)
			.append("writerDto", writerDto)
			.toString();
	}
}
