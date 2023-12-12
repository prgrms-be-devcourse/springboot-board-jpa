package jehs.springbootboardjpa.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;

@Getter
public class ListResponse<T, R> {

    private final List<R> content;
    private final int totalPages;
    private final long totalElements;

    public ListResponse(Page<T> postsPage, Function<T, R> function) {
        this.content = postsPage.getContent().stream().map(function).toList();
        this.totalPages = postsPage.getTotalPages();
        this.totalElements = postsPage.getTotalElements();
    }
}
