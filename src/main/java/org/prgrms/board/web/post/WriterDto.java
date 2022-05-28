package org.prgrms.board.web.post;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.prgrms.board.domain.user.User;

public class WriterDto {

	private final Long id;
	private final String name;

	public WriterDto(User writer) {
		this.id = writer.getId();
		this.name = writer.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("name", name)
			.toString();
	}
}
