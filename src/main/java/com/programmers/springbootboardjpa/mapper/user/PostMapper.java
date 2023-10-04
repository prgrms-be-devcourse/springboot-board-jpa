package com.programmers.springbootboardjpa.mapper.user;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.dto.post.PostCreateRequest;
import com.programmers.springbootboardjpa.dto.post.PostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostCreateRequest postCreateRequest);

    PostResponse toPostResponse(Post post);

}
