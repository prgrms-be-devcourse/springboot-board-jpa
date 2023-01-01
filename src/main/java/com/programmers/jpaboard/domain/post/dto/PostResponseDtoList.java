package com.programmers.jpaboard.domain.post.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponseDtoList {

	private List<PostResponseDto> postResponseDtoList;
}
