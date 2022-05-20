package com.su.gesipan.post;

import com.su.gesipan.user.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostDtoMapper {
    public static Post toPostEntity(PostDto.Create dto, User user){
        return Post.of(dto.getTitle(), dto.getContent(), user);
    }

    public static PostDto.Result toPostResult(Post post){
        return PostDto.Result.of(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
