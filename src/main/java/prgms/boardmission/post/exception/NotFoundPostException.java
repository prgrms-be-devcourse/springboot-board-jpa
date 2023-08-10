package prgms.boardmission.post.exception;

public class NotFoundPostException extends RuntimeException {
    private final static String NOT_FOUND_POST_MESSAGE = "해당 게시글을 찾을 수 없습니다.";

    public NotFoundPostException() {
        super(NOT_FOUND_POST_MESSAGE);
    }
}
