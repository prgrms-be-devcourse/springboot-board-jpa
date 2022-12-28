package devcourse.board.api.model;

import java.util.List;

public record MultipleDataResponse<T>(
        List<T> data
) {
}
