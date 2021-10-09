package org.programmers.project_board.exception;

public class NotFoundException extends RuntimeException {

    private final Object id;

    public NotFoundException(Object id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("%s does not exist.", id);
    }

}
