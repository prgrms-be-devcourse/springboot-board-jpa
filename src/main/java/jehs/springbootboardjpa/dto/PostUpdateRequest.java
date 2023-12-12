package jehs.springbootboardjpa.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateRequest {

    private final String title;
    private final String content;
    private final Long userId;
}
