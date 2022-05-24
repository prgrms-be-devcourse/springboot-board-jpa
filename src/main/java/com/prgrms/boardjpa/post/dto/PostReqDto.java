package com.prgrms.boardjpa.post.dto;

import com.prgrms.boardjpa.domain.Post;
import lombok.*;

import static com.prgrms.boardjpa.Utils.now;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostReqDto {
    private String title;
    private String content;
    private Long userId;

    public Post toEntity(){
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setContent(content);
        newPost.setCreatedAt(now());
        newPost.setCreatedBy(userId.toString());
        return newPost;
    }
}
