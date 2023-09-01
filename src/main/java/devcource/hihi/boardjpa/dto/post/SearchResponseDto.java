package devcource.hihi.boardjpa.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class SearchResponseDto<T> {
    private List<T> data;
    private Long cursor;
}

