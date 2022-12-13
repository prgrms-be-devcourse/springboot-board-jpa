package com.ys.board.common.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entity, Object property) {
        super(String.format("%s Entity not found. by %s", entity.getSimpleName(), property));
    }

    public EntityNotFoundException(Class<?> entity) {
        super(String.format("%s Entity not found. ", entity.getSimpleName()));
    }

}
