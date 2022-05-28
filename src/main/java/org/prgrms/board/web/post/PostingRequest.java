package org.prgrms.board.web.post;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PostingRequest {

	private String title;
	private String content;
	private Long writerId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getWriterId() {
		return writerId;
	}

	public void setWriterId(Long writerId) {
		this.writerId = writerId;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("title", title)
			.append("content", content)
			.append("writerId", writerId)
			.toString();
	}
}