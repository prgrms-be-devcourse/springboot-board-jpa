package prgrms.project.post.controller.response;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        boolean first,
        boolean last
) {

    @Builder
    public PageResponse {
    }

    public static <T> PageResponse<T> of(Slice<T> page) {
        return PageResponse.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getPageable().getPageNumber())
                .pageSize(page.getSize())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
