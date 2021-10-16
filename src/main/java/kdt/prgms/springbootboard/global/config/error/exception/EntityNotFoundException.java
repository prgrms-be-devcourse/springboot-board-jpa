package kdt.prgms.springbootboard.global.config.error.exception;

import kdt.prgms.springbootboard.global.config.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(String message) {
        super(message, ErrorCode.ENTITY_NOT_FOUND);
    }
}