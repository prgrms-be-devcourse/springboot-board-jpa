package org.prgrms.board.domain.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.prgrms.board.domain.user.domain.User;
import org.prgrms.board.domain.user.requset.UserCreateRequest;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
      @Mapping(source = "firstName", target = "name.firstName"),
      @Mapping(source = "lastName", target = "name.lastName"),
      @Mapping(source = "email", target = "email.value")
    })
    User toEntity(UserCreateRequest request);
}
