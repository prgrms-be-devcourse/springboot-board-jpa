package programmers.jpaBoard.exception;

public enum ErrorMessage {
    NOT_FOUND_POST("해당 게시물이 존재하지 않습니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
