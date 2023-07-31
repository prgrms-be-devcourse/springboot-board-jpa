package com.prgrms.board.global.common;

import java.util.List;

import org.springframework.data.domain.Page;

public record PageResponse<T>(
	int total,
	int page,
	int rowsPerPage,
	List<T> items
) {
	public static <T> PageResponse<T> from(Page<T> page) {
		return new PageResponse<>(page.getTotalPages(), page.getNumber(), page.getSize(), page.getContent());
	}
}
