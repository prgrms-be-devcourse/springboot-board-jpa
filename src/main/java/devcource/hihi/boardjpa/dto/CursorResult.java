package devcource.hihi.boardjpa.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CursorResult<T> {
    private List<T> posts;
    private Boolean hasNext;

    public CursorResult(List<T> posts, Boolean hasNext) {
        this.posts = posts;
        this.hasNext = hasNext;
    }
}
