package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import java.util.List;
import java.util.Objects;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDtos {

	private List<PostResponseDto> postResponseDtoList;

	private Long cursorId;

	private Boolean hasNext;

	@Builder
	public PostResponseDtos(
		List<PostResponseDto> postResponseDtoList, Long cursorId, boolean hasNext) {
		this.postResponseDtoList = postResponseDtoList;
		this.cursorId = cursorId;
		this.hasNext = hasNext;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PostResponseDtos that = (PostResponseDtos)o;
		return Objects.equals(getPostResponseDtoList(), that.getPostResponseDtoList())
			&& Objects.equals(getCursorId(), that.getCursorId()) && Objects.equals(getHasNext(),
			that.getHasNext());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPostResponseDtoList(), getCursorId(), getHasNext());
	}
}
