package com.prgrms.jpaboard.domain.post.dto.response;

import com.prgrms.jpaboard.global.common.response.PagingInfoDto;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListDto {
    private PagingInfoDto pagingInfo;
    private List<PostDto> posts;

    public PostListDto(PagingInfoDto pagingInfo, List<PostDto> posts) {
        this.pagingInfo = pagingInfo;
        this.posts = posts;
    }
}
