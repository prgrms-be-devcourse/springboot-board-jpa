package org.prgrms.board.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.requset.UserCreateRequest;
import org.prgrms.board.domain.user.response.UserSearchResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
      @Mapping(source = "firstName", target = "name.firstName"),
      @Mapping(source = "lastName", target = "name.lastName"),
      @Mapping(source = "email", target = "email.value"),
      @Mapping(source = "password", target = "password.value")
    })
    User toEntity(UserCreateRequest request);

    @Mappings({
            @Mapping(source = "name.firstName", target = "firstName"),
            @Mapping(source = "name.lastName", target = "lastName"),
            @Mapping(source = "email.value", target = "email"),
            @Mapping(source = "id", target = "userId")
    })
    UserSearchResponse toSearchResponse(User user);
}
