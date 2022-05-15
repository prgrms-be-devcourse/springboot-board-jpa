package prgrms.project.post.service;

import lombok.Builder;
import org.springframework.data.domain.Slice;

import java.util.List;

public record DefaultPage<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        boolean first,
        boolean last
) {

    @Builder
    public DefaultPage {
    }

    public static <T> DefaultPage<T> of(Slice<T> page) {
        return DefaultPage.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getPageable().getPageNumber())
                .pageSize(page.getSize())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
