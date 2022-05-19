package org.prgrms.board.domain.post.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.prgrms.board.domain.post.domain.Post;
import org.prgrms.board.domain.post.request.PostCreateRequest;
import org.prgrms.board.domain.post.response.PostSearchResponse;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.response.UserSearchResponse;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mappings({
            @Mapping(source = "id", target = "postId"),
            @Mapping(source = "user.name.firstName", target = "user.firstName"),
            @Mapping(source = "user.name.lastName", target = "user.lastName"),
            @Mapping(source = "user.email.value", target = "user.email"),
    })
    PostSearchResponse toSearchResponse(Post post);

    @Mappings({
            @Mapping(source = "searchResponse.firstName", target = "user.name.firstName"),
            @Mapping(source = "searchResponse.lastName", target = "user.name.lastName"),
            @Mapping(source = "searchResponse.email", target = "user.email.value"),
            @Mapping(source = "searchResponse.age", target = "user.age"),
            @Mapping(source = "searchResponse.userId", target = "user.id"),
    })
    Post toEntity(PostCreateRequest postRequest, UserSearchResponse searchResponse);
}
