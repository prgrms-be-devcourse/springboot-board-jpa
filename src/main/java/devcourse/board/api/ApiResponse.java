package devcourse.board.api;

public record ApiResponse<T>(
        String description,
        T data
) {
}
