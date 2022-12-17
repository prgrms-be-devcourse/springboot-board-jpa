package com.programmers.jpaboard.domain.user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

	private Long id;
	private String name;
	private String email;
	private int age;
	private String hobby;
	private List<Long> postIds;
}
