package kdt.springbootboardjpa.controller.response;

import kdt.springbootboardjpa.respository.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private Long createdBy;

    @Builder
    public PostResponse(Long id, String title, String content, Long createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public static PostResponse toPostResponse(Post post){
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getUser().getId())
                .build();
    }
}
