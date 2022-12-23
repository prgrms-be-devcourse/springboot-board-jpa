package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import java.util.List;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CursorResult {
	private List<Post> postList;

	private Long nextCursorId;

	private Boolean hasNext;

	@Builder
	public CursorResult(List<Post> postList, Long nextCursorId, Boolean hasNext) {
		this.postList = postList;
		this.nextCursorId = nextCursorId;
		this.hasNext = hasNext;
	}
}
