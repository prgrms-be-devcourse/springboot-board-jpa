package com.prgrms.jpaboard.domain.post.dto.response;

import com.prgrms.jpaboard.global.common.response.MetaDataDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListDto {
    private MetaDataDto metadata;
    private List<PostDto> posts;

    public PostListDto(MetaDataDto metadata, List<PostDto> posts) {
        this.metadata = metadata;
        this.posts = posts;
    }
}
