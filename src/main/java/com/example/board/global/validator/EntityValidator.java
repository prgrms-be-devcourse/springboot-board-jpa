package com.example.board.global.validator;

import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;

public class EntityValidator {

	public <T> T validateOptionalExists(Optional<T> optional) {
		if (optional.isEmpty()) {
			throw new EntityNotFoundException("exception.entity: {}" + optional.getClass());
		}
		return optional.get();
	}
}

