package devcource.hihi.boardjpa.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageDto<T> {
    private List<T> data;
    private Long prevCursor;
    private Long nextCursor;

}

