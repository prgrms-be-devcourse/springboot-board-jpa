package com.prgrms.devcourse.springjpaboard.domain.post.dto;

import java.util.List;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostSearchResponses {

	private List<Post> posts;

	private Boolean hasNext;

}
